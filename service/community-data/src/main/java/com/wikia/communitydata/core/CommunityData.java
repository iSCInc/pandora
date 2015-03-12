package com.wikia.communitydata.core;

import static com.wikia.jooq.wikicities.Tables.CITY_DOMAINS;
import static com.wikia.jooq.wikicities.Tables.CITY_LIST;
import static com.wikia.jooq.wikicities.Tables.CITY_VARIABLES;
import static com.wikia.jooq.wikicities.Tables.CITY_VERTICALS;

import com.wikia.communitydata.configuration.MysqlConfiguration;
import com.wikia.jooq.wikicities.tables.records.CityListRecord;
import com.wikia.jooq.wikicities.tables.records.CityVariablesRecord;
import com.wikia.jooq.wikicities.tables.records.CityVerticalsRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.types.UShort;

import java.sql.Connection;
import java.sql.DriverManager;

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
  public String getArticlePath() {
    return cityVariablesRecord.getCvValue();
  }

  public static final class Builder {
    private static final UShort ARTICLE_PATH_ID = UShort.valueOf(15);

    private final MysqlConfiguration config;
    private final String domain;

    public Builder(MysqlConfiguration config, String domain) {
      this.config = config;
      this.domain = domain;
    }

    public CommunityData build() {
      String url = String.format("jdbc:mysql://%s:%s/%s", config.getHost(),
                                 config.getPort(), config.getDb());

      CommunityData communityData = null;

      try (Connection conn = DriverManager
          .getConnection(url, config.getUser(), config.getPassword())) {
        Record result = DSL.using(conn, SQLDialect.MYSQL)
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

        CityListRecord listRecord = result.into(CITY_LIST);
        CityVerticalsRecord verticalsRecord = result.into(CITY_VERTICALS);
        CityVariablesRecord variablesRecord = result.into(CITY_VARIABLES);

        communityData = new CommunityData(listRecord, verticalsRecord, variablesRecord);
      } catch (Exception e) {

      }

      return communityData;
    }
  }
}
