# Pandora-example

Copy/Paste service example

# How to use it?

To run the server:

```bash
cp example.yaml.sample example.yaml
../gradlew run # launch application
```

To make a request from the server:

```bash
curl -v http://localhost:8080/HelloWorld/Your_Name_Here
```

To run the tests:

```bash
../gradlew test # run all tests
```

# What is currently in?

ExampleServiceApplication - Main class that start all things.
Here you can setup your resources, healthChecks, writers.
At least one resource and healthCheck is needed.

ExampleConfiguration - Contains whatever you want.
Data here are created from example.yaml by magic(reflection)
 
ExampleResource - Controller handling your requests. 
Contains entry points, and logic what, and how service should return

ExampleHealthCheck - Self explanation class

banner.txt - [Why this is important](https://dropwizard.github.io/dropwizard/manual/core.html#banners)
