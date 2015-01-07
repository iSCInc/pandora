package com.wikia.pandora;

import com.wikia.pandora.api.HalMessageBodyWriter;
import com.wikia.pandora.resources.ArticlesResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        final ArticlesResource articles = new ArticlesResource();

        environment.jersey().register(articles);
        environment.jersey().register(HalMessageBodyWriter.class);
    }


}
