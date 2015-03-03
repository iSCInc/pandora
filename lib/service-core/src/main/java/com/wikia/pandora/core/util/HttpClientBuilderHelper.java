package com.wikia.pandora.core.util;


import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;

import org.apache.http.client.HttpClient;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

public class HttpClientBuilderHelper {
  public static HttpClient createHttpClient(Environment environment,
                                            PandoraConfiguration configuration,
                                            String name) {
    /// This HttpClient is deprecated should we use it? DropWizard using this.
    return new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build(name);
  }
}
