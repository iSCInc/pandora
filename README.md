# Pandora

An API proxy service for productizing the Mercury API with the
[guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign).


There is a draft design document [here](DESIGN.md). If you want to learn more
about this project that’s a good place to start.

## Configuration

To be able to use our consul bundle you need to create a "~/.gradle/gradle.properties" file in your local directory (for location of the file in Windows consult gradle documentation) and add the following properties:
```
archivaUsername=YOUR_USERNAME
archivaPassword=YOUR_PASSWORD
archivaUrl=INTERNAL_MAVEN_REPO
dockerRegistryHost=DOCKER_REGISTERY_HOST
testConsulHost=CONSUL_HOST
marathonUrl=MESOS_URL
```

## Usage

Launching unit tests:
```bash
./gradlew unitTest
```

Launching integration tests:
```bash
./gradlew integrationTest
```

Launching all tests:
```bash
./gradlew test
```

To run the server:

```bash
cp pandora-examples/pandora.yaml.sample pandora-examples/pandora.yaml
# launch the server
gradle pandora-service:run
```

To make a request from the server:

```bash
curl -v http://localhost:8080/articles/muppet/Kermit%20the%20Frog
```

To run the tests:

```bash
gradle test
```

## License

Copyright © 2014 Wikia

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
