package com.wikia.mobileconfig.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.ArrayList;

public class MobileApplicationConfig {
    @JsonCreator
    public MobileApplicationConfig() {
    }

    public String getSelfUrl(String platform, String appTag, String appVersion) {
        return String.format("/configurations/platform/%s/app/%s/version/%s", platform, appTag, appVersion);
    }

    /**
     * @desc Returns mocked modules data - this data will be pulled from files stored in CEPH most likely
     * @return
     * @throws IOException
     */
    public ArrayList getMockedModules() throws IOException {
        ArrayList modules = new ArrayList();
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
        modules.add(module);

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
        modules.add(module);

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
        modules.add(module);

        return modules;
    }

}
