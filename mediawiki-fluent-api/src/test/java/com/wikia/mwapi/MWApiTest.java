package com.wikia.mwapi;

import com.wikia.mwapi.domain.ApiResponse;

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
    MWApi mwApi = Mockito.mock(MWApi.class);
    Mockito.when(mwApi.get()).thenCallRealMethod();
    Mockito.when(mwApi.wikia(Mockito.anyString())).thenCallRealMethod();
    Mockito.when(mwApi.queryAction()).thenCallRealMethod();
    Mockito.when(mwApi.titles(Mockito.anyString())).thenCallRealMethod();

    InputStream jsonStream = new ByteArrayInputStream(
        FixtureHelpers.fixture("fixtures/kermit-the-frog-title.json").getBytes(StandardCharsets.UTF_8)
    );
    Mockito.when(mwApi.handleMWRequest(Mockito.anyString())).thenReturn(jsonStream);

    ApiResponse response = mwApi
        .wikia("muppet")
        .queryAction()
        .titles("Kermit the Frog")
        .get();
    assertEquals("Kermit the Frog", response.getQuery().getFirstPage().getTitle());
    assertEquals(50, response.getQuery().getFirstPage().getPageId());
  }


}