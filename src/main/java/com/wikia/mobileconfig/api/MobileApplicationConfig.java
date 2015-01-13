package com.wikia.mobileconfig.api;

import ch.halarious.core.HalResource;
import ch.halarious.core.HalLink;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MobileApplicationConfig implements HalResource {
    @HalLink
    public final String self;
    public final List modules;

    public MobileApplicationConfig(
            String platform,
            String appTag,
            String appVersion
    ) throws IOException {
        this.self = String.format("/configurations/platform/%s/app/%s/version/%s", platform, appTag, appVersion);
        this.modules = new ArrayList();

        ObjectMapper mapper = new ObjectMapper();

        MobileApplicationModule module = mapper.readValue(
                "{" +
                        "\"type\": \"chat\", " +
                        "\"payload\": {\n" +
                            "\"chatUrl\": \"http://chat.wikia-services.net\"\n" +
                        "}" +
                "}",
                MobileApplicationModule.class
        );
        this.modules.add(module);

        module = mapper.readValue(
                "{" +
                        "\"type\": \"url\", " +
                        "\"payload\": {\n" +
                            "\"url\": \"http://first.wikia.com/wiki/FirstUrl\",\n" +
                            "\"icon\": \"http://first.wikia.com/fav.ico\"\n" +
                        "}" +
                "}",
                MobileApplicationModule.class
        );
        this.modules.add(module);

        module = mapper.readValue(
                "{" +
                        "\"type\": \"url\", " +
                        "\"payload\": {\n" +
                            "\"url\": \"http://second.wikia.com/wiki/SecondUrl\",\n" +
                            "\"icon\": \"http://second.wikia.com/fav.ico\"\n" +
                        "}" +
                "}",
                MobileApplicationModule.class
        );
        this.modules.add(module);
    }
}
