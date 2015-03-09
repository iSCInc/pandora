# Pandora-example

Copy/Paste service example

# How to use it?

To run the server:

```bash
cp example.yaml.sample example.yaml
../gradlew run # launch application
# or from the main folder:
gradle service:notifications:run

```

And put proper storage host name into the config:
example.yaml:redis.endpoint property

To make a request from the server:

```bash
curl -v http://localhost:8080/HelloWorld/Your_Name_Here
```

To run the tests:

```bash
../gradlew test # run all tests
```

# What is currently in?

NotificationsServiceApplication - Main class that start all things.
Here you can setup your resources, healthChecks, writers.
At least one resource and healthCheck is needed.

NotificationsConfiguration - Contains whatever you want.
Data here are created from example.yaml by magic(reflection)

NotificationsResource - Controller handling your requests.
Contains entry points, and logic what, and how service should return

NotificationsHealthCheck - Self explanation class

banner.txt - [Why this is important](https://dropwizard.github.io/dropwizard/manual/core.html#banners)
