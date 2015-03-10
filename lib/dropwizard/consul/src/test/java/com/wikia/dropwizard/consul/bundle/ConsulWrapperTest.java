package com.wikia.dropwizard.consul.bundle;

import org.junit.Test;

import io.dropwizard.util.Duration;

import static org.junit.Assert.assertEquals;

public class ConsulWrapperTest {

  @Test
  public void testDurationConversionToConsulFormat() {
    assertEquals(ConsulWrapper.formatDurationToMilliseconds(Duration.parse("2 seconds")),
                 "2000ms"
    );
  }
}
