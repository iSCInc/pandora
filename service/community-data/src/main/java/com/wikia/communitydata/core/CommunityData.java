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
import de.ailis.pherialize.exceptions.UnserializeException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.types.UShort;
import org.slf4j.LoggerFactory;

public class CommunityData {
  private final CityListRecord cityListRecord;
  private final CityVerticalsRecord cityVerticalsRecord;
  private final String articlePath;

  private CommunityData(CityListRecord cityListRecord,
                        CityVerticalsRecord cityVerticalsRecord,
                        String articlePath) {
    this.cityListRecord = cityListRecord;
    this.cityVerticalsRecord = cityVerticalsRecord;
    this.articlePath = articlePath;
  }

  @JsonProperty
  public String getDbName() {
    return cityListRecord.getCityDbname();
  }

  @JsonProperty
  public String getLang() {
    return cityListRecord.getCityLang();
  }

  @JsonProperty
  public String getSiteName() {
    return cityListRecord.getCitySitename();
  }

  @JsonProperty
  public String getDescription() {
    return cityListRecord.getCityDescription();
  }

  @JsonProperty
  public String getMainPageTitle() {
    return cityListRecord.getCityTitle();
  }

  @JsonProperty
  public String getVertical() {
    return cityVerticalsRecord.getVerticalName();
  }

  @JsonProperty
  public boolean getIsAdult() {
    return cityListRecord.getCityAdult() == 1;
  }

  @JsonProperty
  public String getArticlePath() {
    return articlePath;
  }

  public static final class Builder {
    private static final UShort ARTICLE_PATH_ID = UShort.valueOf(15);
    private static final String DEFAULT_ARTICLE_PATH = "/wiki/$1";

    private final DSLContext db;
    private final String domain;

    public Builder(DSLContext db, String domain) {
      this.db = db;
      this.domain = domain;
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
        CityListRecord listRecord = result.into(CITY_LIST);
        CityVerticalsRecord verticalsRecord = result.into(CITY_VERTICALS);
        String articlePath = parseArticlePath(result.into(CITY_VARIABLES));

        communityData = new CommunityData(listRecord, verticalsRecord, articlePath);
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
