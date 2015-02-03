package com.wikia.mwapi.util;

import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class URIBuilderHelper {

  public static <T> void addParameter(URIBuilder builder, String parameterName,
                                      List<T> clPropEnumList) {
    if (clPropEnumList != null && clPropEnumList.size() > 0) {
      String joinString = String.join("|",
                                      clPropEnumList
                                          .stream()
                                          .map(T::toString)
                                          .collect(Collectors.<String>toList()));
      builder.addParameter(parameterName, joinString);
    }
  }

  public static void addParameterIfNotEmpty(URIBuilder builder, String parameterName, int number) {
    if (number != 0) {
      builder.addParameter(parameterName, String.valueOf(number));
    }
  }

  public static void addParameterIfNotEmpty(URIBuilder builder, String parameterName,
                                            String string) {
    if (string != null && !Objects.equals(string, "")) {
      builder.addParameter(parameterName, string);
    }
  }

  public static void addParameterIfNotEmpty(URIBuilder builder, String parameterName,
                                            boolean bool) {
    if (bool) {
      builder.addParameter(parameterName, "");
    }
  }

  public static void addParameterIfNotEmpty(URIBuilder builder, String parameterName,
                                            Enum<?> en) {
    if (en != null) {
      builder.addParameter(parameterName, en.toString());
    }
  }

  public static void addParameterIfNotEmpty(URIBuilder builder, String parameterName,
                                            DateTime dateTime) {
    if (dateTime != null) {
      builder.addParameter(parameterName, dateTime.toString());
    }
  }
}
