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

To check service health:
```bash
curl -v http://127.0.0.1:8081/healthcheck
```
A weird thing which I decided on is a health check for an external service. Our service strongly depends on it,
so I found it beneficial to implement a health check of it. The health check is called `apps-deployer`
and can be found on the list returned in a response above.

To make a request from the server:
```bash
curl -v http://127.0.0.1:8080/configurations/platform/android/app/witcher
```

## License

Copyright © 2014 Wikia

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.