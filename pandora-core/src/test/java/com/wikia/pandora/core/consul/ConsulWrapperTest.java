package com.wikia.pandora.core.consul;

import com.wikia.pandora.test.FastTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import io.dropwizard.util.Duration;

import static org.junit.Assert.assertEquals;

@Category(FastTest.class)
public class ConsulWrapperTest {

  @Test
  public void testDurationConversionToConsulFormat() {
    assertEquals(ConsulWrapper.formatDurationToMilliseconds(Duration.parse("2 seconds")),
                 "2000ms"
    );
  }
}
