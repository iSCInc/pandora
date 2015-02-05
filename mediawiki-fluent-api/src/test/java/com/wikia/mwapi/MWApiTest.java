package com.wikia.mwapi;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.enumtypes.query.properties.CLPropEnum;
import com.wikia.mwapi.enumtypes.query.properties.RVPropEnum;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import io.dropwizard.testing.FixtureHelpers;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class MWApiTest {

  @Test
  public void testBuildUrl() {
    HttpClient clientMock = Mockito.mock(HttpClient.class);
    String url = MWApi.createBuilder(clientMock)
        .wikia("muppet")
        .queryAction()
        .titles("Kermit the Frog")
        .url();
    assertEquals("http://muppet.wikia.com/api.php?action=query&format=json&titles=Kermit+the+Frog",
                 url);
  }

  @Test
  public void testBuildAdvanceUrl() {
    HttpClient clientMock = Mockito.mock(HttpClient.class);

    String url = MWApi.createBuilder(clientMock)
        .wikia("stargate")
        .queryAction()
        .allPages()
        .categories()
        .cllimit(5)
        .clcontinue("test")
        .clprop(CLPropEnum.sortkey)
        .url();

    assertEquals(
        "http://stargate.wikia.com/api.php?action=query&format=json&list=allpages&prop=categories&clprop=sortkey&cllimit=5&clcontinue=test",
        url);

  }

  @Test
  public void testUrlWithRevisions() {
    HttpClient clientMock = Mockito.mock(HttpClient.class);
    String url = MWApi.createBuilder(clientMock)
        .wikia("stargate")
        .queryAction()
        .titles("Aphofis")
        .prop().revisions()
        .rvlimit(1)
        .rvprop(RVPropEnum.content)
        .url();
    assertEquals(
        "http://stargate.wikia.com/api.php?action=query&format=json&titles=Aphofis&prop=revisions&rvprop=content&rvlimit=1",
        url);
  }

  @Test
  public void testSomething() {
    HttpClient clientMock = Mockito.mock(HttpClient.class);
    String url = MWApi.createBuilder(clientMock)
        .domain("jedisomething.de")
        .queryAction()
        .titles("something")
        .revisions()
        .rvprop(RVPropEnum.user, RVPropEnum.comment, RVPropEnum.content)
        .categories().cllimit(10)
        .url();
    assertEquals(
        "http://jedisomething.de/api.php?action=query&format=json&titles=something&prop=revisions%7Ccategories&rvprop=user%7Ccomment%7Ccontent&cllimit=10",
        url);
  }

  @Test
  public void testUrlWithDomain() {
    HttpClient clientMock = Mockito.mock(HttpClient.class);
    String url = MWApi.createBuilder(clientMock)
        .domain("stargate.custom.domain")
        .queryAction()
        .titles("chapa'ai")
        .url();

    assertEquals(
        "http://stargate.custom.domain/api.php?action=query&format=json&titles=chapa%27ai",
        url);
  }

  @Test
  public void testGetWithTitle() throws IOException {
    String wikia = "muppet";
    String title = "Kermit the Frog";
    HttpClient clientMock = Mockito.mock(HttpClient.class);
    String url = MWApi.createBuilder(clientMock)
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .url();

    HttpClient httpClient = mockHttpClientForHandleMwRequest("fixtures/kermit-the-frog-title.json");

    ApiResponse response = MWApi.createBuilder(httpClient)
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .get();
    assertEquals("Kermit the Frog", response.getQuery().getFirstPage().getTitle());
    assertEquals(50, response.getQuery().getFirstPage().getPageId());

    ArgumentCaptor<HttpUriRequest> argument = ArgumentCaptor.forClass(HttpUriRequest.class);
    Mockito.verify(httpClient).execute(argument.capture());
    assertEquals(url, argument.getValue().getURI().toString());
  }


  public HttpClient mockHttpClientForHandleMwRequest(String fixturePath) throws IOException {
    HttpClient httpClient = Mockito.mock(HttpClient.class);
    HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    HttpEntity httpEntity = Mockito.mock(HttpEntity.class);

    InputStream jsonStream = new ByteArrayInputStream(
        FixtureHelpers.fixture(fixturePath).getBytes(StandardCharsets.UTF_8)
    );

    Mockito.when(httpEntity.getContent()).thenReturn(jsonStream);
    Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
    Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponse);

    return httpClient;
  }

}
