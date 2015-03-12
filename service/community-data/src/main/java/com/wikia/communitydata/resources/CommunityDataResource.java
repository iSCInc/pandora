package com.wikia.communitydata.resources;

import static com.wikia.jooq.wikicities.Tables.CITY_DOMAINS;
import static com.wikia.jooq.wikicities.Tables.CITY_LIST;
import static com.wikia.jooq.wikicities.Tables.CITY_VARIABLES;
import static com.wikia.jooq.wikicities.Tables.CITY_VERTICALS;

import com.wikia.communitydata.configuration.MysqlConfiguration;
import com.wikia.jooq.wikicities.tables.records.CityListRecord;
import com.wikia.jooq.wikicities.tables.records.CityVariablesRecord;
import com.wikia.jooq.wikicities.tables.records.CityVerticalsRecord;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.types.UShort;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/community-data")
@Produces(RepresentationFactory.HAL_JSON)
public class CommunityDataResource {
  private static final UShort ARTICLE_PATH_ID = UShort.valueOf(15);
  private static final String DEFAULT_ARTICLE_PATH = "/wiki/$1";

  private final RepresentationFactory representationFactory;
  private final MysqlConfiguration wikiDb;

  @Inject
  public CommunityDataResource(RepresentationFactory representationFactory, MysqlConfiguration wikiDb) {
    this.representationFactory = representationFactory;
    this.wikiDb = wikiDb;
  }

  @GET
  @Path("/{domain}")
  @Timed
  public Representation getData(@PathParam("domain") String domain) {
    String url = String.format("jdbc:mysql://%s:%s/%s", wikiDb.getHost(), wikiDb.getPort(), wikiDb.getDb());
    Representation representation = representationFactory.newRepresentation();

    try (Connection conn = DriverManager.getConnection(url, wikiDb.getUser(), wikiDb.getPassword())) {
      Record result = DSL.using(conn, SQLDialect.MYSQL)
          .select(CITY_LIST.CITY_DBNAME, CITY_LIST.CITY_LANG, CITY_LIST.CITY_SITENAME,
                  CITY_LIST.CITY_ADULT,
                  CITY_LIST.CITY_TITLE, CITY_LIST.CITY_DESCRIPTION, CITY_VERTICALS.VERTICAL_NAME,
                  CITY_VARIABLES.CV_VALUE)
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

      representation
          .withProperty("articlePath", variablesRecord.getCvValue())
          .withProperty("dbName", listRecord.getCityDbname())
          .withProperty("lang", listRecord.getCityLang())
          .withProperty("siteName", listRecord.getCitySitename())
          .withProperty("description", listRecord.getCityDescription())
          .withProperty("mainPageTitle", listRecord.getCityTitle())
          .withProperty("vertical", verticalsRecord.getVerticalName());
    } catch (Exception e) {

    }

    return representation;
  }
}
