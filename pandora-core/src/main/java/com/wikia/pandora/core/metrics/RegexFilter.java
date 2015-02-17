package com.wikia.pandora.core.metrics;

import com.google.common.collect.ImmutableSet;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegexFilter implements MetricFilter {
  private ArrayList<Pattern> includes;
  private ArrayList<Pattern> excludes;

  public RegexFilter(ImmutableSet<String> includes, ImmutableSet<String> excludes) {
    this.includes = compilePatterns(includes);
    this.excludes = compilePatterns(excludes);
  }

  private ArrayList<Pattern> compilePatterns(ImmutableSet<String> patternStrings) {
    ArrayList<Pattern> patterns = new ArrayList<>();
    patternStrings.forEach(s -> patterns.add(Pattern.compile(s)));

    return patterns;
  }

  private boolean includesMatches(String name) {
    for (Pattern p : includes) {
      if (p.matcher(name).matches()) {
        return true;
      }
    }

    return false;
  }

  private boolean excludesMatches(String name) {
    for (Pattern p : excludes) {
      if (p.matcher(name).matches()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean matches(String name, Metric metric) {
    boolean useInclude = !includes.isEmpty();
    boolean useExclude = !excludes.isEmpty();

    if (useInclude && useExclude) {
      return includesMatches(name) || !excludesMatches(name);
    } else if (useInclude && !useExclude) {
      return includesMatches(name);
    } else if (!useInclude && useExclude) {
      return excludesMatches(name);
    }

    return true;
  }
}
