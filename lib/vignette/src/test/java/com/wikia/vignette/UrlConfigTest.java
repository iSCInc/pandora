package com.wikia.vignette;

import static org.junit.Assert.*;
import org.junit.Test;

public class UrlConfigTest {

  @Test
  public void testBuilder() {
    final UrlConfig uc = new UrlConfig.Builder()
        .isArchive(true)
        .replaceThumbnail(true)
        .timestamp(1234)
        .relativePath("a/ab/foo.png")
        .bucket("muppet")
        .baseURL("http://vignette1.wikia.nocookie.net")
        .domainShardCount(3)
        .build();
    assertTrue(uc.isArchive);
    assertTrue(uc.replaceThumbnail);
    assertEquals((Integer)3, uc.domainShardCount);
    assertEquals(uc.relativePath, "a/ab/foo.png");
  }

  @Test(expected=IllegalStateException.class)
  public void testBuilderStateException() {
    final UrlConfig uc = new UrlConfig.Builder()
        .isArchive(true)
        .replaceThumbnail(true)
        .timestamp(12343)
        .relativePath("a/ab/foo.png")
        .build();
  }
}
