package com.wikia.mwapi;

import com.wikia.mwapi.domain.ApiResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
    String url = MWApi.createBuilder()
        .wikia("muppet")
        .queryAction()
        .titles("Kermit the Frog")
        .url();
    assertEquals(url, "http://muppet.wikia.com/api.php?action=query&titles=Kermit+the+Frog&format=json");
  }

  @Test
  public void testGet() throws IOException {
    String wikia = "muppet";
    String title = "Kermit the Frog";
    String url = MWApi.createBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .url();
    HttpUriRequest request = new HttpGet(url);

    HttpClient httpClient = Mockito.mock(HttpClient.class);
    HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    HttpEntity httpEntity = Mockito.mock(HttpEntity.class);

    InputStream jsonStream = new ByteArrayInputStream(
        FixtureHelpers.fixture("fixtures/kermit-the-frog-title.json").getBytes(StandardCharsets.UTF_8)
    );
    Mockito.when(httpEntity.getContent()).thenReturn(jsonStream);
    Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
    Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponse);

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

}
