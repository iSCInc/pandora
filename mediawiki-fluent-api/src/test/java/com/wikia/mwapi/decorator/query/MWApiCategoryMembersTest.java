package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.MWApi;
import com.wikia.mwapi.enumtypes.query.properties.CMPropEnum;

import org.apache.http.client.HttpClient;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MWApiCategoryMembersTest {

  @Test
  public void testCategoryMemberTest() {
    HttpClient clientMock = Mockito.mock(HttpClient.class);
    String url = MWApi.createBuilder(clientMock)
        .wikia("stargate")
        .queryAction()
        .categorymembers()
        .cmtitle("Category:Writers")
        .cmlimit(3)
        .cmprop(CMPropEnum.IDS, CMPropEnum.TITLE, CMPropEnum.TYPE)
        .url();
    assertEquals(
        "http://stargate.wikia.com/api.php?action=query&format=json&list=categorymembers&cmtitle=Category%3AWriters&cmprop=ids%7Ctitle%7Ctype&cmlimit=3",
        url);
  }
}
