package com.wikia.mwapi.decorator.query.list;

import static org.junit.Assert.assertEquals;

import com.wikia.mwapi.MWApi;
import com.wikia.mwapi.enumtypes.query.properties.ACPropEnum;

import org.apache.http.client.HttpClient;
import org.junit.Test;
import org.mockito.Mockito;

public class MWApiAllCategoriesTest {

  @Test
  public void testAllCategoriesUrl() {
    HttpClient clientMock = Mockito.mock(HttpClient.class);
    String url = MWApi.createBuilder(clientMock)
        .wikia("stargate")
        .queryAction()
        .allcategories()
        .aclimit(11)
        .acfrom("Earth")
        .acto("Tilk")
        .acprop(ACPropEnum.SIZE)
        .url();

    assertEquals(
        "http://stargate.wikia.com/api.php?action=query&format=json&list=allcategories&acfrom=Earth&acto=Tilk&aclimit=11&acprop=size",
        url);
  }
}