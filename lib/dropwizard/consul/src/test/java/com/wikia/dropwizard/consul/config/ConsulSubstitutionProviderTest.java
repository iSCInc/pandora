package com.wikia.dropwizard.consul.config;

import com.wikia.gradle.ConsulKeyValueConfig;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.dropwizard.configuration.ConfigurationSourceProvider;

import static com.wikia.dropwizard.consul.config.ConsulSubstitutionProvider.convertStreamToString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsulSubstitutionProviderTest {

  ConsulSubstitutionProvider provider;
  ConfigurationSourceProvider baseProvider;

  @Before
  public void initialize() {
    ConsulKeyValueConfig keyValueConfig = mock(ConsulKeyValueConfig.class);
    StrSubstitutor substitutor = new StrSubstitutor(new ConsulVariableLookup(keyValueConfig))
        .setVariablePrefix(ConsulVariableLookup.SUBSTITUTOR_PREFIX);

    baseProvider = mock(ConfigurationSourceProvider.class);
    provider = new ConsulSubstitutionProvider(baseProvider, substitutor);

    Map<String, String> config = new HashMap<>();
    config.put("SOME_INT", "5");
    config.put("SOME_STR", "hello there");
    config.forEach((k, v) -> when(keyValueConfig.get(k)).thenReturn(v));
  }

  @Test
  public void testIntSubstitution() throws IOException {
    when(baseProvider.open(any())).thenReturn(getStreamForString("foo: ${consul:SOME_INT}"));
    assertEquals(convertStreamToString(provider.open("path")), "foo: 5");
  }

  @Test
  public void testStringSubstitution() throws IOException {
    when(baseProvider.open(any())).thenReturn(getStreamForString("foo: ${consul:SOME_STR}"));
    assertEquals(convertStreamToString(provider.open("path")), "foo: hello there");
  }

  @Test
  public void testDefaultValue() throws IOException {
    when(baseProvider.open(any()))
        .thenReturn(getStreamForString("foo: ${consul:UNDEFINED_VALUE:42}"));
    assertEquals(convertStreamToString(provider.open("path")), "foo: 42");
  }

  @Test(expected = RuntimeException.class)
  public void testUndefinedNoDefaultValue() throws IOException {
    when(baseProvider.open(any())).thenReturn(getStreamForString("foo: ${consul:UNDEFINED_VALUE}"));
    convertStreamToString(provider.open("path"));
  }

  private InputStream getStreamForString(String s) {
    return new ByteArrayInputStream(s.getBytes());
  }
}
