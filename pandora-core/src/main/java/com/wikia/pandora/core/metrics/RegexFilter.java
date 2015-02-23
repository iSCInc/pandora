package com.wikia.pandora.core.metrics;

import com.google.common.collect.ImmutableSet;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;

import java.util.regex.Pattern;

public class RegexFilter implements MetricFilter {

  private Pattern includes;
  private Pattern excludes;
  private boolean excludeFromInclude;

  public RegexFilter(ImmutableSet<String> includes, ImmutableSet<String> excludes) {
    this(includes, excludes, false);
  }

  /**
   * @param includes           includes patterns
   * @param excludes           excludes patterns
   * @param excludeFromInclude if true, exclude from include, if false include from exclude
   */
  public RegexFilter(ImmutableSet<String> includes, ImmutableSet<String> excludes,
                     boolean excludeFromInclude) {
    this.excludeFromInclude = excludeFromInclude;
    this.includes = compilePatterns(includes);
    this.excludes = compilePatterns(excludes);
  }

  private Pattern compilePatterns(ImmutableSet<String> patternStrings) {
    Pattern pattern = null;
    if (patternStrings != null && !patternStrings.isEmpty()) {
      String combinePattern = String.join("|", patternStrings);
      pattern = Pattern.compile(combinePattern);
    }
    return pattern;
  }

  private boolean includesMatches(String name) {
    return includes.matcher(name).matches();
  }

  private boolean excludesMatches(String name) {
    return excludes.matcher(name).matches();
  }

  @Override
  public boolean matches(String name, Metric metric) {
    boolean useInclude = (includes != null);
    boolean useExclude = (excludes != null);

    if (useInclude && useExclude) {
      return matchesWithExcludeMethod(name);
    } else if (useInclude) {
      return includesMatches(name);
    } else if (useExclude) {
      return !excludesMatches(name);
    }
    return true;
  }

  private boolean matchesWithExcludeMethod(String name) {
    if (excludeFromInclude) {
      return includesMatches(name) && !excludesMatches(name);
    } else {
      return includesMatches(name) || !excludesMatches(name);
    }
  }
}
