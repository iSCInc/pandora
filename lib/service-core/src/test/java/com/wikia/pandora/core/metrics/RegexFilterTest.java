package com.wikia.pandora.core.metrics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

public class RegexFilterTest {

  @Test
  public void testIncludeMatches() {
    ImmutableSet<String> includes = new ImmutableSet.Builder<String>()
        .add("([a-z]){5}")
        .add("([0-4]){3}")
        .add("simple")
        .add("([a-z]+)[0-9]{2}")
        .build();

    RegexFilter regexFilter = new RegexFilter(includes, null);

    assertTrue(regexFilter.matches("abcde", null));
    assertTrue(regexFilter.matches("simple", null));
    assertTrue(regexFilter.matches("test09", null));
    assertTrue(regexFilter.matches("012", null));

    assertFalse(regexFilter.matches("abcdef", null));
    assertFalse(regexFilter.matches("4567", null));
    assertFalse(regexFilter.matches("", null));
  }


  @Test
  public void testExcludeMatches() {
    ImmutableSet<String> excludes = new ImmutableSet.Builder<String>()
        .add("([a-z]){5}")
        .add("([0-4]){3}")
        .add("simple")
        .add("([a-z]+)[0-9]{2}")
        .build();

    RegexFilter regexFilter = new RegexFilter(null, excludes);

    assertTrue(regexFilter.matches("abcdef", null));
    assertTrue(regexFilter.matches("4567", null));
    assertTrue(regexFilter.matches("", null));

    assertFalse(regexFilter.matches("abcde", null));
    assertFalse(regexFilter.matches("simple", null));
    assertFalse(regexFilter.matches("test09", null));
    assertFalse(regexFilter.matches("012", null));
  }

  @Test
  public void testExcludeTakesPrecedenceMatches() {
    ImmutableSet<String> includes = new ImmutableSet.Builder<String>()
        .add("([a-z]){5}[0-9]{2}")
        .add("([0-4]){2}")
        .build();
    ImmutableSet<String> excludes = new ImmutableSet.Builder<String>()
        .add("([a-z])+[0-9]{2}")
        .add("([0-9]+)")
        .build();

    RegexFilter regexFilter = new RegexFilter(includes, excludes, true);

    assertTrue(regexFilter.matches("abcde12", null));
    assertTrue(regexFilter.matches("12", null));

    assertFalse(regexFilter.matches("abcdef12", null));
  }

  @Test
  public void testIncludeTakesPrecedenceMatches() {
    ImmutableSet<String> includes = new ImmutableSet.Builder<String>()
        .add("([a-z]+)[0-9]{2}")
        .add("([0-9]+)")
        .build();
    ImmutableSet<String> excludes = new ImmutableSet.Builder<String>()
        .add("([a-z]){5}[0-9]{2}")
        .add("([0-4]){2}")
        .build();

    RegexFilter regexFilter = new RegexFilter(includes, excludes, false);

    assertTrue(regexFilter.matches("abcdef12", null));

    assertFalse(regexFilter.matches("12", null));
    assertFalse(regexFilter.matches("abcde12", null));
  }

  @Test
  public void testMatchesWithoutIncludesOrExcludes() {
    RegexFilter regexFilter = new RegexFilter(null, null);

    assertTrue(regexFilter.matches("abcdef", null));
    assertTrue(regexFilter.matches("4567", null));
    assertTrue(regexFilter.matches("", null));
    assertTrue(regexFilter.matches("abcde", null));
    assertTrue(regexFilter.matches("simple", null));
    assertTrue(regexFilter.matches("test09", null));
    assertTrue(regexFilter.matches("012", null));
  }
}
