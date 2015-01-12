# mobile-config-service

An API service with the [guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign) for Mobile Apps Team.

The API provides modules configuration for mobile applications created in Wikia.

## Usage

To run the server:

```bash
cp mobile-config.yml.sample mobile-config.yml
# launch the server
gradle run
```

To make a request from the server:

```bash
curl -v http://localhost:8080/configuration/v1/ios/id123456789/1.0
```

To run the tests:

```bash
gradle test
```


## License

Copyright © 2014 Wikia

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.