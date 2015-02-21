# Discussion Service


# How to use it?

To run the server:

```bash
cp discussionservice.yaml.sample discussionservice.yaml
../gradlew run # launch application
```

To make a request from the server:

```bash
curl -v http://localhost:8080/forums
```

To run the tests:

```bash
../gradlew test # run all tests
```

# What is currently in?

DiscussionServiceApplication - Main class that start all things.
Here you can setup your resources, healthChecks, writers.
At least one resource and healthCheck is needed.

DiscussionServiceConfiguration - Contains whatever you want.
Data here are created from example.yaml by magic(reflection)
 
ForumResource - Controller handling your requests.
Contains entry points, and logic what, and how service should return

DiscussionServiceHealthCheck - Self explanation class

banner.txt - [Why this is important](https://dropwizard.github.io/dropwizard/manual/core.html#banners)
