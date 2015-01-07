package com.wikia.pandora.api;

import ch.halarious.core.HalLink;
import ch.halarious.core.HalResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URLEncoder;
import java.util.Map;

public class Article implements HalResource {

    public final Integer id;
    public final String title;
    public final String content;

    @HalLink
    public final String self;


    @JsonCreator
    public Article(@JsonProperty("id") Integer id,
                   @JsonProperty("title") String title,
                   @JsonProperty("content") String content,
                   @JsonProperty("_links") Map<String, Object> links) throws java.io.IOException {
        this.id = id;
        this.title = title;
        this.content = content;
        this.self = String.format("/articles/%s", URLEncoder.encode(this.title, "UTF-8"));
    }

    private Article(Builder builder) {
        id = builder.id;
        title = builder.title;
        content = builder.content;
        self = builder.self;
    }

    public static final class Builder {
        private Integer id;
        private String title;
        private String content;
        private String self;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Article build() throws java.io.IOException {
            this.self = String.format("/articles/%s", URLEncoder.encode(this.title, "UTF-8"));
            return new Article(this);
        }
    }
}
