# Pandora

An API proxy service for productizing the Mercury API with the
[guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign).


There is a draft design document [here](DESIGN.md). If you want to learn more
about this project that’s a good place to start.

## Usage

Launching unit tests:
```bash
./gradlew unitTest
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
