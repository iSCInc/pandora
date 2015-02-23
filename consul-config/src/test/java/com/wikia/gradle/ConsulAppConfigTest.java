package com.wikia.gradle;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.model.kv.Value;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ConsulAppConfigTest {
  @Test
  public void testGetConfig() {
    List<Value> expectedValues = new ArrayList<>();
    HashMap<String, String> configs = new HashMap<>();

    configs.put("SOME_IP", "127.0.0.1");
    configs.put("SOME_PORT", "12345");
    configs.put("SOME_HOST", "www.wikia.com");

    configs.forEach((k, v) -> expectedValues.add(generateValue("config/demo-app/prod/" + k, v)));

    Consul consul = mock(Consul.class);
    KeyValueClient keyValueClient = mock(KeyValueClient.class);

    when(consul.keyValueClient()).thenReturn(keyValueClient);
    when(keyValueClient.getValues(anyString())).thenReturn(expectedValues);

    ConsulAppConfig consulAppConfig = new ConsulAppConfig(consul);
    assertEquals(configs, consulAppConfig.getConfig("demo-app", "prod"));
  }

  private Value generateValue(String key, String value) {
    Value v = new Value();
    v.setKey(key);
    v.setValue(Base64.encodeBase64String(value.getBytes()));

    return v;
  }
}
