package com.wikia.mobileconfig.resources;

import com.wikia.mobileconfig.service.AppsDeployerList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.io.IOException;

import io.dropwizard.testing.junit.ResourceTestRule;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link com.wikia.mobileconfig.resources.AppListResource}.
 */
public class AppListResourceTest {
  private static final HttpClient httpClientMock = mock(HttpClient.class);

  private static final AppsDeployerList listService = new AppsDeployerList(httpClientMock, "test-domain");

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new AppListResource(listService)).build();

  class HttpGetWithURL extends ArgumentMatcher<HttpGet> {

    private String url;

    public HttpGetWithURL(String url) {
      this.url = url;
    }

    public boolean matches(Object httpGet) {
      return url.equals(((HttpGet) httpGet).getURI().toString());
    }
  }

  @Test
  public void getAppList() throws IOException {

    when(httpClientMock.execute(any(HttpGet.class), any(ResponseHandler.class))).thenReturn(
        "[{ \"test-key\": \"test data\" }]");

    for (int i = 0; i < 10; ++i) {
      String response = resources.client()
          .resource("/appList/platform/test-platform")
          .get(String.class);
      assert(response).contains("test-key");
    }

    verify(httpClientMock, times(1))
        .execute(argThat(new HttpGetWithURL("http://test-domain/api/app-configuration/")), any(ResponseHandler.class));
    reset(httpClientMock);
  }
}
