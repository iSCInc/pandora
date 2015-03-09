# Wikia Vignette Java Client

A [Vignette](http://github.com/Wikia/vignette) client for Java.

## Installation
For now, this library is only available on Wikia's internal Archiva server.

Gradle
```groovy
repositories {
    // definition of internal archiva server
}

dependencies {
    compile 'com.wikia:vignette:0.1'
}
```

## Usage

To use the library, first, create a configuration object. The configuration object is used to locate
the image.

```java
UrlConfig config new UrlConfig.Builder()
				.bucket("tests")
				.relativePath("a/ab/foo.png")
				.timestamp(12343);
```

Then create a generator, which is responsible for modifying the original and generating a url.
Build it, and call the `url()` method to generate the URL.

```java
UrlGenerator generator = new UrlGenerator.Builder(config)
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
