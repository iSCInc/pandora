# Wikia Vignette Java Client

A [Vignette](http://github.com/Wikia/vignette) client for Java.

## Usage

To use the library, first, create a configuration object.

```java
UrlConfig config new UrlConfig.Builder()
				.timestamp(12343)
				.relativePath("a/ab/foo.png")
				.baseURL("http://images.vignette.com")
				.bucket("tests");
```

Then create a generator, build it, and call the `url()` method to generate the URL.

```java
UrlGenerator generator = new UrlGenerator.Builder()
				.config(config)
				.width(200)
				.height(200)
				.thumbnail()
				.build();

String url = generator.url();
```

## Build

`gradle build`

## Test

`gradle test`
