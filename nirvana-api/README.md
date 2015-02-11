# Nirvana API
This project generates code for requesting resources from Wikia's Nirvana API. Under the hood, it relies on the [swagger](http://swagger.io/) 2.0 definitions of the Nirvana API in Wikia's [MediaWiki repo](https://github.com/Wikia/app/blob/dev/includes/wikia/api/swagger/nirvana.json). The code is generated using Wikia's fork of [swagger-codegen](https://github.com/Wikia/swagger-codegen/tree/develop_2.0), which is modified to include a `String wikia` parameter as the first parameter to each API call, before the project's compilation phase.

## Using the API
### Add nirvana-api as dependency
In your settings.gradle
```groovy
include 'nirvana-api'
project(':nirvana-api').projectDir = new File(settingsDir, 'relative/path/to/nirvana-api')
```
In your build.gradle
```groovy
dependencies {
  compile project(':nirvana-api')
}
```
Next time you build, the Nirvana API will generate. The source will also be deleted as part of the `clean` task. It will not be regenerated if the `src` directory exists in the nirvana-api directory.
### Calling the API
```java
import com.wikia.nirvana.api.ArticlesApi;
import com.wikia.nirvana.model.ContentResult;

...

try {
  ContentResult cr = new ArticlesApi().getAsSimpleJson("muppet", 50);
} catch (ApiException e) { }

// do stuff with cr
```
