package com.wikia.mobileconfig.service.application;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wikia.mobileconfig.exceptions.CephAppsListServiceException;
import com.wikia.pandora.core.testhelper.TestHelper;

import io.dropwizard.testing.FixtureHelpers;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

public class CephAppsListServiceTest {

  @Test
  public void testIsValidAppTag() throws Exception {
    HttpClient
        client =
        TestHelper
            .getHttpClientMock("http://ceph:80/mobile-configuration-service-apps-list/platform",
                               "[{ \"tag\": \"platform\" }]");
    CephAppsListService cephAppsList = new CephAppsListService(client, "ceph", "80");
    assertTrue(cephAppsList.isValidAppTag("platform", "platform"));
    assertFalse(cephAppsList.isValidAppTag("platform", "failtag"));
  }

  @Test
  public void testGetAppList() throws Exception {
    HttpClient
        client =
        TestHelper
            .getHttpClientMock("http://ceph:80/mobile-configuration-service-apps-list/platform",
                               FixtureHelpers.fixture("fixtures/small-configuration"));
    CephAppsListService cephAppsList = new CephAppsListService(client, "ceph", "80");
    List<HashMap<String, Object>> test = cephAppsList.getAppList("platform");
    assertEquals(test.size(), 1);
    HashMap<String, Object> singleEntry = test.get(0);
    assertEquals(singleEntry.get("name"), "Five Nights at Freddy's");
    assertEquals(singleEntry.get("android_release"), "com.wikia.singlewikia.fivenightsatfreddys");
  }

  @Test(expected = CephAppsListServiceException.class)
  public void testGetAppListException() throws Exception {
    HttpClient
        client =
        TestHelper.getHttpClientMock("http://ceph:80/whatever/platform", "");
    CephAppsListService cephAppsList = new CephAppsListService(client, "ceph", "80");
    cephAppsList.getAppList("platform");
  }

  @Test
  public void testIsUp() throws Exception {
    HttpClient client = mock(HttpClient.class);
    HttpResponse response = mock(HttpResponse.class);
    StatusLine statusLine = mock(StatusLine.class);
    when(statusLine.getStatusCode()).thenReturn(200);
    when(response.getStatusLine()).thenReturn(statusLine);
    when(client.execute(any())).thenReturn(response);

    CephAppsListService cephAppsList = new CephAppsListService(client, "ceph", "80");
    assertTrue(cephAppsList.isUp());
  }

  @Test
  public void testIsUpFail() throws Exception {
    HttpClient client = mock(HttpClient.class);
    HttpResponse response = mock(HttpResponse.class);
    StatusLine statusLine = mock(StatusLine.class);
    when(statusLine.getStatusCode()).thenReturn(404);
    when(response.getStatusLine()).thenReturn(statusLine);
    when(client.execute(any())).thenReturn(response);

    CephAppsListService cephAppsList = new CephAppsListService(client, "ceph", "80");
    assertFalse(cephAppsList.isUp());
  }

}
