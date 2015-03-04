package com.wikia.pandora.core.testhelper;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class TestHelper {

  public static HttpClient getHttpClientMock(Map<String, String> map) throws IOException {
    HttpClient client = Mockito.mock(HttpClient.class);

    for (Map.Entry<String, String> entry : map.entrySet()) {
      addMockRequest(client, entry.getKey(), entry.getValue());
    }

    return client;
  }

  public static HttpClient getHttpClientMock(String url, String result) throws IOException {
    HttpClient client = Mockito.mock(HttpClient.class);
    addMockRequest(client, url, result);
    return client;
  }

  private static void addMockRequest(HttpClient client, String url, String result)
      throws IOException {
    when(client.execute(argThat(new HttpUriRequestMatch(url)),
                        Matchers.<ResponseHandler<String>>any()))
        .thenReturn(result);
  }

  private static class HttpUriRequestMatch extends ArgumentMatcher<HttpUriRequest> {

    private String key;

    public HttpUriRequestMatch(String key) {
      this.key = key;
    }

    @Override
    public boolean matches(Object argument) {
      HttpUriRequest request = (HttpUriRequest) argument;
      return request != null && Objects.equals(request.getURI().toASCIIString(), key);
    }
  }
}
