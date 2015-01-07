package com.wikia.pandora.api;

import static org.fest.assertions.api.Assertions.assertThat;
import static io.dropwizard.testing.FixtureHelpers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import junit.framework.TestCase;
import org.junit.Test;

public class ArticleTest extends TestCase {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void testDeserializeFromJson() throws java.io.IOException {
        final Article article = new Article.Builder()
                .content("foo content")
                .title("foo")
                .id(10)
                .build();
        final Article deserializedArticle = MAPPER.readValue(fixture("fixtures/article.json"), Article.class);
        assertThat(article).isEqualsToByComparingFields(deserializedArticle);
    }


}