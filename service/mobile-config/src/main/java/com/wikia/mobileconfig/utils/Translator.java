package com.wikia.mobileconfig.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.MobileConfigApplication;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Translates values based on the translation json file
 */
public class Translator {
  private static Translator instance = new Translator();

  private static final String TRANSLATION_FILE_PATH =
      "translations/translations.%s.json";

  private final ConcurrentHashMap<String, Future<Map<String, String>>> translationCache = new ConcurrentHashMap<>();

  private final ObjectMapper mapper = new ObjectMapper();

  public static Translator getInstance() {
    return instance;
  }

  private Translator() {
  }

  private Map<String, String> loadTranslations(String langCode) throws IOException {
    String translationFilePath = String.format(TRANSLATION_FILE_PATH, langCode);

    // Avoid classloader caching the file by accessing via URL 
    URL resource = getClass().getClassLoader().getResource(translationFilePath);

    if (resource == null) {
      throw new FileNotFoundException(translationFilePath);
    }

    return (Map<String, String>) this.mapper.readValue(
        resource.openStream(),
        Map.class
    );
  }

  private Future<Map<String, String>> loadAndCacheTranslation(String langCode) {
      CompletableFuture<Map<String, String>> futureTranslations = new CompletableFuture<>();
      Future<Map<String, String>> existingFuture = translationCache.putIfAbsent(langCode, futureTranslations);

      if (existingFuture == null) {
        try {
          futureTranslations.complete(loadTranslations(langCode));
        } catch (Exception e) {
          futureTranslations.completeExceptionally(e);
        }

        return futureTranslations;
      } else {
        return existingFuture;
      }
  }

  private Map<String, String> getTranslationMap(String langCode) {
    Future<Map<String, String>> translationSource = translationCache.get(langCode);

    if (translationSource == null) {
      translationSource = loadAndCacheTranslation(langCode);
    }

    Map<String, String> translations = null;
    try {
      translations = translationSource.get();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      MobileConfigApplication.LOGGER.error("InterruptedException while waiting for translation file to load: {}", langCode);
    } catch (ExecutionException e) {
      MobileConfigApplication.LOGGER.error(
          String.format("Error while loading translation file: %s", e.getCause().toString()), e.getCause());
    } catch (CancellationException e) {
      MobileConfigApplication.LOGGER.error("Cancelled loading translation file for language {}: {}", langCode, e.toString());
    }

    if (translations == null) {
      translationCache.remove(langCode, translations);
      translations = Collections.emptyMap();
    }

    return translations;
  }

  public String translate(String langCode, String key) {
    Map<String, String> map = getTranslationMap(langCode);

    String result = map.getOrDefault(key, key);

    return result;
  }
}
