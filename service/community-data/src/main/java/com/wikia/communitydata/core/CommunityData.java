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
  private static final String DEFAULT_ARTICLE_PATH = "/wiki/$1";

  private final CityListRecord cityListRecord;
  private final CityVerticalsRecord cityVerticalsRecord;
  private final CityVariablesRecord cityVariablesRecord;

  private CommunityData(CityListRecord cityListRecord,
                        CityVerticalsRecord cityVerticalsRecord,
                        CityVariablesRecord cityVariablesRecord) {
    this.cityListRecord = cityListRecord;
    this.cityVerticalsRecord = cityVerticalsRecord;
    this.cityVariablesRecord = cityVariablesRecord;
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
                    .put("value", cityVariablesRecord.getCvValue())
                    .build()
            ),
            "error during deserialization",
            e
        );

        path = DEFAULT_ARTICLE_PATH;
      }
    }

    return path;
  }

  public static final class Builder {
    private static final UShort ARTICLE_PATH_ID = UShort.valueOf(15);

    private final DSLContext db;
    private final String domain;

    public Builder(DSLContext db, String domain) {
      this.db = db;
      this.domain = domain;
    }

    public CommunityData build() {
      CommunityData communityData = null;

      try {
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
          CityVariablesRecord variablesRecord = result.into(CITY_VARIABLES);

          communityData = new CommunityData(listRecord, verticalsRecord, variablesRecord);
        }
      } catch (Exception e) {
        LoggerFactory.getLogger(this.getClass()).error(
            appendEntries(
                new ImmutableMap.Builder<String, String>()
                    .put("domain", domain)
                    .build()
            ),
            "error fetching configuration",
            e
        );
      }

      return communityData;
    }
  }
}
