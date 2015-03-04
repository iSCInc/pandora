package com.wikia.pandora.core.util;


import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;

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
