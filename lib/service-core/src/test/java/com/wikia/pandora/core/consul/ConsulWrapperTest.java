package com.wikia.pandora.core.consul;

import static org.junit.Assert.assertEquals;

import io.dropwizard.util.Duration;
import org.junit.Test;

public class ConsulWrapperTest {

  @Test
  public void testDurationConversionToConsulFormat() {
    assertEquals(ConsulWrapper.formatDurationToMilliseconds(Duration.parse("2 seconds")),
                 "2000ms"
    );
  }
}
