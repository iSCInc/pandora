package com.wikia.communitydata.core;

import static com.wikia.jooq.wikicities.Tables.CITY_DOMAINS;
import static com.wikia.jooq.wikicities.Tables.CITY_LIST;
import static com.wikia.jooq.wikicities.Tables.CITY_VARIABLES;
import static com.wikia.jooq.wikicities.Tables.CITY_VERTICALS;
import static net.logstash.logback.marker.Markers.appendEntries;

import com.wikia.jooq.wikicities.tables.records.CityListRecord;
import com.wikia.jooq.wikicities.tables.records.CityVariablesRecord;
import com.wikia.jooq.wikicities.tables.records.CityVerticalsRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import de.ailis.pherialize.Pherialize;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.types.UShort;
import org.slf4j.LoggerFactory;

public class CommunityData {
  private final String dbName;
  private final String lang;
  private final String siteName;
  private final String description;
  private final String mainPageTitle;
  private final String vertical;
  private final boolean isAdult;
  private final String articlePath;

  private CommunityData(String dbName, String lang, String siteName, String description,
                       String mainPageTitle, String vertical, boolean isAdult,
                       String articlePath) {
    this.dbName = dbName;
    this.lang = lang;
    this.siteName = siteName;
    this.description = description;
    this.mainPageTitle = mainPageTitle;
    this.vertical = vertical;
    this.isAdult = isAdult;
    this.articlePath = articlePath;
  }

  @JsonProperty
  public String getDbName() {
    return dbName;
  }

  @JsonProperty
  public String getLang() {
    return lang;
  }

  @JsonProperty
  public String getSiteName() {
    return siteName;
  }

  @JsonProperty
  public String getDescription() {
    return description;
  }

  @JsonProperty
  public String getMainPageTitle() {
    return mainPageTitle;
  }

  @JsonProperty
  public String getVertical() {
    return vertical;
  }

  @JsonProperty
  public boolean getIsAdult() {
    return isAdult;
  }

  @JsonProperty
  public String getArticlePath() {
    return articlePath;
  }

  public static final class Builder {
    private static final UShort ARTICLE_PATH_ID = UShort.valueOf(15);
    private static final String DEFAULT_ARTICLE_PATH = "/wiki/$1";

    private DSLContext db;
    private String domain;

    public Builder setDb(DSLContext db) {
      this.db = db;
      return this;
    }

    public Builder setDomain(String domain) {
      this.domain = domain;
      return this;
    }

    public CommunityData build() throws Exception {
      CommunityData communityData = null;

      Record result = db
          .select(CITY_LIST.CITY_DBNAME, CITY_LIST.CITY_LANG, CITY_LIST.CITY_SITENAME,
                  CITY_LIST.CITY_ADULT, CITY_LIST.CITY_TITLE, CITY_LIST.CITY_DESCRIPTION,
                  CITY_VERTICALS.VERTICAL_NAME, CITY_VARIABLES.CV_VALUE)
          .from(CITY_LIST
                    .join(CITY_DOMAINS)
                    .on(CITY_DOMAINS.CITY_ID.equal(CITY_LIST.CITY_ID))
                    .join(CITY_VERTICALS)
                    .on(CITY_VERTICALS.VERTICAL_ID.equal(CITY_LIST.CITY_VERTICAL))
                    .leftOuterJoin(CITY_VARIABLES)
                    .on(CITY_VARIABLES.CV_CITY_ID.equal(CITY_LIST.CITY_ID)
                            .and(CITY_VARIABLES.CV_VARIABLE_ID.equal(ARTICLE_PATH_ID))))
          .where(CITY_DOMAINS.CITY_DOMAIN.equal(domain))
          .fetchOne();

      if (result != null) {
        CityListRecord cityListRecord = result.into(CITY_LIST);
        CityVerticalsRecord cityVerticalsRecord = result.into(CITY_VERTICALS);

        communityData = new CommunityData(
            cityListRecord.getCityDbname(),
            cityListRecord.getCityLang(),
            cityListRecord.getCitySitename(),
            cityListRecord.getCityDescription(),
            cityListRecord.getCityTitle(),
            cityVerticalsRecord.getVerticalName(),
            cityListRecord.getCityAdult() == 1,
            parseArticlePath(result.into(CITY_VARIABLES))
        );
      }

      return communityData;
    }

    private String parseArticlePath(CityVariablesRecord cityVariablesRecord) {
      String path = cityVariablesRecord.getCvValue();

      if (path == null) {
        path = DEFAULT_ARTICLE_PATH;
      } else {
        try {
          path = Pherialize.unserialize(path).toString();
        } catch (Exception e) {
          LoggerFactory.getLogger(this.getClass()).error(
              appendEntries(
                  new ImmutableMap.Builder<String, String>()
                      .put("article_path", cityVariablesRecord.getCvValue())
                      .build()
              ),
              "error deserializing article path, returning default",
              e
          );

          path = DEFAULT_ARTICLE_PATH;
        }
      }

      return path;
    }
  }
}
