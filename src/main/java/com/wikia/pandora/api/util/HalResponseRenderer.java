package com.wikia.pandora.api.util;

import ch.halarious.core.HalExclusionStrategy;
import ch.halarious.core.HalResource;
import ch.halarious.core.HalSerializer;
import com.google.gson.GsonBuilder;

public class HalResponseRenderer implements ResponseRendererInterface {

    private final GsonBuilder gsonBuilder;
    private final Object objectToRender;


    public HalResponseRenderer(HalResource objectToRender) {
        gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(HalResource.class, new HalSerializer())
                .setExclusionStrategies(new HalExclusionStrategy());
        this.objectToRender = objectToRender;
    }

    @Override
    public String render() {
        return gsonBuilder.create().toJson(this.objectToRender, HalResource.class);
    }
}
