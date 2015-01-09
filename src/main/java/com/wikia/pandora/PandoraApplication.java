package com.wikia.pandora;

import com.wikia.pandora.api.HalMessageBodyWriter;
import com.wikia.pandora.gateway.MercuryGateway;
import com.wikia.pandora.resources.ArticlesResource;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;

public class PandoraApplication extends Application<PandoraConfiguration> {
    public static void main(String[] args) throws Exception {
        new PandoraApplication().run(args);
    }

    @Override
    public String getName() {
        return "Pandora";
    }

    @Override
    public void initialize(Bootstrap<PandoraConfiguration> bootstrap) {
    }

    @Override
    public void run(PandoraConfiguration configuration,
                    Environment environment) {

        final HttpClient httpClient = new HttpClientBuilder(environment).using(configuration.getHttpClientConfiguration())
                .build("gateway-client");

        final MercuryGateway mercuryGateway = new MercuryGateway(httpClient);

        final ArticlesResource articles = new ArticlesResource(mercuryGateway);

        final PandoraHealthCheck healthCheck =
                new PandoraHealthCheck();
        environment.healthChecks().register("pandora", healthCheck);

        environment.jersey().register(articles);
        environment.jersey().register(HalMessageBodyWriter.class);
    }


}
