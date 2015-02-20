package com.wikia.mobileconfig.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.MobileConfigApplication;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


/**
 * Translates values based on the translation json file
 */
public class Translator {

  private static Translator instance = null;

  private static final String TRANSLATION_FILE_PATH =
      "/translations/translations.%s.json";

  private Map<String, Map<String, String>> translationCache;

  private ObjectMapper mapper = new ObjectMapper();

  private class TranslationFile {

  }

  private Translator() {
    translationCache = new HashMap<String, Map<String, String>>();
  }

  public static Translator getInstance() {
    if (instance == null) {
      synchronized(Translator.class) {
        if (instance == null) {
          instance = new Translator();
        }
      }
    }
    return instance;
  }

  private void loadTranslations(String langCode) {
    Map<String, String> result;
    try {
      byte[] encoded = Files.readAllBytes(Paths.get(
          getClass().getResource(String.format(TRANSLATION_FILE_PATH, langCode)).getPath()));

      String translationFile = new String(encoded, Charset.defaultCharset());

      result = (Map) this.mapper.readValue(
          translationFile,
          Map.class
      );
    } catch (Exception e) {
      result = new HashMap<String, String>();
      MobileConfigApplication.LOGGER.error(
          "Error while parsing translation file: " + e.toString());
    }

    translationCache.put(langCode, result);
  }

  private Map<String, String> getTranslationMap(String langCode) {
    if (!translationCache.containsKey(langCode)) {
      loadTranslations(langCode);
    }

    return translationCache.get(langCode);
  }

  public String translate(String langCode, String key) {
    Map<String, String> map = getTranslationMap(langCode);

    String result = map.getOrDefault(key, key);

    return result;
  }
}
