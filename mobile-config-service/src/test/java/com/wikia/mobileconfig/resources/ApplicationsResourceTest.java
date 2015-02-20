package com.wikia.mobileconfig.resources;

import com.wikia.mobileconfig.gateway.AppsDeployerList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.io.IOException;

import io.dropwizard.testing.junit.ResourceTestRule;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ApplicationsResource}.
 */
public class ApplicationsResourceTest {
  private static final HttpClient HTTP_CLIENT_MOCK = mock(HttpClient.class);

  private static final AppsDeployerList
      LIST_SERVICE = new AppsDeployerList(HTTP_CLIENT_MOCK, "test-domain");

  @ClassRule
  public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
      .addResource(new ApplicationsResource(LIST_SERVICE)).build();

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

    when(HTTP_CLIENT_MOCK.execute(any(HttpGet.class), any(ResponseHandler.class))).thenReturn(
        "[{ \"test-key\": \"test data\" }]");

    for (int i = 0; i < 10; ++i) {
      String responseData = RESOURCES.client()
          .target("/applications/platform/test-platform")
          .request()
          .get(String.class);
      assertThat(responseData).contains("test-key");
    }

    verify(HTTP_CLIENT_MOCK, times(1))
        .execute(argThat(new HttpGetWithURL("http://test-domain/api/app-configuration/")), any(ResponseHandler.class));
    reset(HTTP_CLIENT_MOCK);
  }
}
