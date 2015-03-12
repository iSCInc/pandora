package com.wikia.communitydata.resources;

import com.wikia.communitydata.configuration.MysqlConfiguration;
import com.wikia.jooq.wikicities.tables.CityList;
import com.wikia.jooq.wikicities.tables.records.CityListRecord;
import com.wikia.jooq.wikicities.tables.records.CityVariablesRecord;
import com.wikia.jooq.wikicities.tables.records.CityVerticalsRecord;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.types.UShort;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static org.jooq.impl.DSL.*;
import static com.wikia.jooq.wikicities.Tables.*;

@Path("/community-data")
@Produces(RepresentationFactory.HAL_JSON)
public class CommunityDataResource {
  private static final UShort ARTICLE_PATH_ID = UShort.valueOf(15);
  private static final String DEFAULT_ARTICLE_PATH = "/wiki/$1";

  private final RepresentationFactory representationFactory;
  private final MysqlConfiguration wikiDb;

  public CommunityDataResource(RepresentationFactory representationFactory, MysqlConfiguration wikiDb) {
    this.representationFactory = representationFactory;
    this.wikiDb = wikiDb;
  }

  @GET
  @Path("/{id}")
  @Timed
  public Representation getHelloWorld(@PathParam("id") int id) {
    String url = String.format("jdbc:mysql://%s:%s/%s", wikiDb.getHost(), wikiDb.getPort(), wikiDb.getDb());
    Representation representation = representationFactory.newRepresentation();

    try (Connection conn = DriverManager.getConnection(url, wikiDb.getUser(), wikiDb.getPassword())) {
      Record result = DSL.using(conn, SQLDialect.MYSQL)
          .select(CITY_LIST.CITY_DBNAME, CITY_LIST.CITY_LANG, CITY_LIST.CITY_SITENAME,
                  CITY_LIST.CITY_TITLE, CITY_LIST.CITY_DESCRIPTION, CITY_VERTICALS.VERTICAL_NAME,
                  CITY_VARIABLES.CV_VALUE)
          .from(CITY_LIST
                    .join(CITY_VERTICALS)
                    .on(CITY_VERTICALS.VERTICAL_ID.equal(CITY_LIST.CITY_VERTICAL))
                    .leftOuterJoin(CITY_VARIABLES)
                    .on(CITY_VARIABLES.CV_CITY_ID.equal(CITY_LIST.CITY_ID)
                            .and(CITY_VARIABLES.CV_VARIABLE_ID.equal(ARTICLE_PATH_ID))))
          .where(CITY_LIST.CITY_ID.equal(id))
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
