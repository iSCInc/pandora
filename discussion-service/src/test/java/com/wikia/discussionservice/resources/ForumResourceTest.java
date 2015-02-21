package com.wikia.discussionservice.resources;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ForumResourceTest {

  @Test
  public void testAdd() {
    String str = "TestNG is working fine";
    assertEquals("TestNG is working fine", str);
  }

}
