package com.wikia.mobileconfig.gateway;
import com.wikia.pandora.core.testhelper.TestHelper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.List;
import io.dropwizard.testing.FixtureHelpers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
public class AppsDeployerListContainerTest {
  @Test
  public void testIsValidAppTag() throws Exception {
    HttpClient
        client =
        TestHelper.getHttpClientMock("http://test/api/app-configuration/",
                                     "[{ \"platform\": \"tag\" }]");
    AppsDeployerListContainer appsDeployer = new AppsDeployerListContainer(client, "test");
    assertTrue(appsDeployer.isValidAppTag("platform", "tag"));
    assertFalse(appsDeployer.isValidAppTag("failplatform", "failtag"));
  }
  @Test
  public void testGetAppList() throws Exception {
    HttpClient
        client =
        TestHelper.getHttpClientMock("http://test/api/app-configuration/",
                                     FixtureHelpers.fixture("fixtures/small-configuration"));
    AppsDeployerListContainer appsDeployer = new AppsDeployerListContainer(client, "test");
    List<HashMap<String, Object>> test = appsDeployer.getAppList("platform");
    assertEquals(test.size(), 1);
    HashMap<String, Object> singleEntry = test.get(0);
    assertEquals(singleEntry.get("name"), "Five Nights at Freddy's");
    assertEquals(singleEntry.get("android_release"), "com.wikia.singlewikia.fivenightsatfreddys");
  }
  @Test
  public void testIsUp() throws Exception {
    HttpClient
        client =
        TestHelper.getHttpClientMock("http://test/api/", "{healthy:ok}");
    AppsDeployerListContainer appsDeployer = new AppsDeployerListContainer(client, "test");
    assertTrue(appsDeployer.isUp());
  }
  @Test
  public void testIsUpFail() throws Exception {
    HttpClient client = Mockito.mock(HttpClient.class);
    when(client.execute(Matchers.<HttpUriRequest>any(), Matchers.<ResponseHandler<String>>any()))
        .thenThrow(ClientProtocolException.class);
    AppsDeployerListContainer appsDeployer = new AppsDeployerListContainer(client, "test");
    assertFalse(appsDeployer.isUp());
  }
}
