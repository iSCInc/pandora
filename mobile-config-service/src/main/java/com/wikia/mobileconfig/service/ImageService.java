package com.wikia.mobileconfig.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import javax.ws.rs.core.Response;

/**
 * A class responsible for getting mobile applications images from our Ceph buckets via HTTP
 */
public class ImageService {

  private static final String
      CEPH_URL_FORMAT = "http://%s:%s/mobile-configuration-service/images/%s";

  private final HttpClient httpClient;
  private final String cephDomain;
  private final String cephPort;

  public ImageService(
          Environment environment,
          MobileConfigConfiguration configuration
  ) {
    this.httpClient = new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build("image-service");
    this.cephDomain = configuration.getCephDomain();
    this.cephPort = configuration.getCephPort();
  }

  public byte[] getImage(String filename) throws IOException {

    byte[] image = null;

    try {
      image = this.executeHttpRequest(createCephUrl(filename));
    } catch (IOException e) {
      MobileConfigApplication.logger.error(
          String.format("Exception caught when requesting Image %s:%s", filename, e.toString())
      );
    }

    return image;
  }

  private String createCephUrl(String filename) {
    return String.format(CEPH_URL_FORMAT, cephDomain, cephPort, filename);
  }

  private byte[] executeHttpRequest(final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
    HttpResponse response = this.httpClient.execute(httpGet);
    if (response.getStatusLine().getStatusCode() != Response.Status.OK.getStatusCode()) {
      String err = response.getStatusLine().toString();
      MobileConfigApplication.logger.debug(
          String.format("Invalid status code received when requesting Image %s:%s", requestUrl, err));
      throw new IOException("Invalid response received: " + err);
    }

    byte[] content = EntityUtils.toByteArray(response.getEntity());
    return content;
  }
}
