# Pandora

An API proxy service for productizing the Mercury API with the
[guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign).

There is a draft design document [here](DESIGN.md).

## Code Layout

 * `com.wikia.api.*`: API request handlers live here.
 * `com.wikia.service.*`: Glue between gateways and API handlers.
 * `com.wikia.gateway.*`: External gateways reside here (e.g. MediaWiki client)

## Usage

To run the server:

```bash
cp pandora.yaml.sample pandora.yaml
# launch the server
gradle run
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
