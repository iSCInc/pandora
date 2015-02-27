package com.wikia.vignette;

import static org.junit.Assert.*;
import org.junit.Test;

import java.net.URISyntaxException;

public class UrlGeneratorTest {

    protected UrlConfig.Builder sampleConfig() {
        return new UrlConfig.Builder()
                .isArchive(false)
                .replaceThumbnail(true)
                .timestamp(12343)
                .relativePath("a/ab/foo.png")
                .baseURL("http://images.vignette.com")
                .bucket("tests");
    }

    @Test(expected=IllegalStateException.class)
    public void testModePathThumbnailErrorWidth() {
        String path = new UrlGenerator.Builder()
                .thumbnail()
                .build().modePath();
    }

    @Test(expected=IllegalStateException.class)
    public void testModePathThumbnailErrorHeight() {
        String path = new UrlGenerator.Builder()
                .thumbnail()
                .width(200)
                .build().modePath();
    }

    @Test
    public void testModePathOriginal() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .original()
                .build();
        String modePath = generator.modePath();
        assertEquals("", modePath);
    }

    @Test
    public void testModePathThumbnail() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(sampleConfig().build())
                .width(200)
                .height(200)
                .thumbnail()
                .build();
        assertEquals("/thumbnail/width/200/height/200", generator.modePath());
    }

    @Test
    public void testPathThumbnail() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(sampleConfig().build())
                .width(200)
                .height(200)
                .thumbnail()
                .build();
        assertEquals("/tests/images/a/ab/foo.png/revision/latest/thumbnail/width/200/height/200", generator.path());
    }

    @Test
    public void testImagePathOriginal() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(sampleConfig().build())
                .original()
                .build();

        assertEquals(generator.imagePath(), "tests/images/a/ab/foo.png/revision/latest");
    }

    @Test
    public void testUrlThumbnail() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(sampleConfig().build())
                .width(200)
                .height(200)
                .thumbnail()
                .build();
        try {
            assertEquals("http://images.vignette.com/tests/images/a/ab/foo.png/revision/latest/thumbnail/width/200/height/200", generator.url());
        } catch (URISyntaxException e) {
            fail("URISyntaxException should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testUrlThumbnailQueryParams() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(sampleConfig().build())
                .width(200)
                .height(200)
                .pathPrefix("es")
                .replace(true)
                .fill("black")
                .thumbnail()
                .build();
        try {
            assertEquals("http://images.vignette.com/tests/images/a/ab/foo.png/revision/latest/thumbnail/width/200/height/200?path-prefix=es&replace=true&fill=black", generator.url());
        } catch (URISyntaxException e) {
            fail("URISyntaxException should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testComputeShardStringZero() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(sampleConfig().domainShardCount(0).build())
                .build();
        assertEquals("", generator.computeShardString("/a/ab/foo.png"));
    }

    @Test
    public void testComputeShardStringNonZero() {
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(sampleConfig().domainShardCount(1).build())
                .build();
        assertNotEquals("", generator.computeShardString("/a/ab/foo.png"));
    }

    @Test
    public void testDomainShardZero() {
        UrlConfig config = sampleConfig().build();
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(config)
                .build();
        String url = generator.domainShard(config.baseURL, "/a/ab/foo.png");
        assertEquals(config.baseURL, url);
    }

    @Test
    public void testDomainShard() {
        UrlConfig config = sampleConfig()
                .domainShardCount(1)
                .baseURL("http://vignette<SHARD>.wikia.com")
                .build();
        UrlGenerator generator = new UrlGenerator.Builder()
                .config(config)
                .build();
        String url = generator.domainShard(config.baseURL, "/a/ab/foo.png");
        assertEquals("http://vignette1.wikia.com", url);
    }
}