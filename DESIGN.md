# Pandora Design Document

This document provides an overview of the Pandora project.

## Table of Contents

- [Pandora Design Document](#pandora-design-document)
    - [Table of Contents](#table-of-contents)
    - [What is Pandora?](#what-is-pandora)
    - [What is the Service and API Project?](#what-is-the-service--api-project)
    - [Goals](#goals)
    - [Existing Mercury API](#existing-mercury-api)
    - [Milestones](#milestones)
    - [Implementation Notes](#implementation-notes)
        - [Architecture & Data Flow](#architecture--data-flow)
    - [Open Questions](#open-questions)
    - [FAQ](#faq)

## What is Pandora?

Pandora is a service that provides an
[facade](http://en.wikipedia.org/wiki/Facade_pattern) in the form of an API for
the article experience. It will

 * Provide a simplified
	 [guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign)
	 compliant content interface to articles.
 * Enable rapid prototyping and iteration of the above
 * Hide the complexity of MediaWiki
 * Decouple content which will enable
  * Hypermedia API designs
  * Better caching
  * Ability to fan-out requests

## What is the Service & API Project?

The Service & API Project is a project that is operating within the platform
group to deliver the Pandora service to production. The Service & API Project
has the following objectives:

 1. Deliver a replacement API for Mercury that satisfies the [API design
    guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign). This
    is Pandora.
 2. Improve the “last mile” of service delivery. Configuring, provisioning, and
    deploying services to production requires [a lot of
    configuration](https://github.com/Wikia/chef-repo/search?p=2&q=vignette&utf8=%E2%9C%93).
    The team will provide tooling and documentation to help make this process
    easier.
 3. Provide common patterns, tooling and guidelines for service development to
    the rest of engineering to help accellerate the transition to SOA.

## Goals

Short to mid term:

 1. Provide an API that satisfies the [design
    guide](https://github.com/Wikia/guidelines/tree/master/APIDesign) for mobile
    web.
 2. Provide an archetype API that can be used for other services.
 3. Provide a foundation upon which to expand the service and API to support
    mobile apps and partners.
 4. Improve our infrastructure for building and deploying services.

Long term:

The long-term goal of the Pandora project is to provide a standardized set of
external API endpoints to all external-facing Wikia functionality.

## Existing Mercury API

An example query from the current Mercury API can be found
[here](http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit%20the%20Frog).
This will be used as a reference point for mobile web. Here is a
[snapshot](https://gist.github.com/drsnyder/db9649bc0fa2daa2f41e) of the
payload.

## Milestones

 1. [Phase 1 proposal](design/PHASE-1.md)

## Implementation Notes

 * The service will be written in Java version 1.8.
 * We are using [dropwizard](http://dropwizard.io/) as the framework.

### Architecture & Data Flow

![Pandora Architecture](assets/pandora-arch.png)

This is similar to how the Mercury service is currently designed. Some of the
content components are called out (comments, media, users as examples) to
suggest that they may be migrated to separate services in the future. The list
is not exhaustive.

To improve performance, a caching layer (varnish) will be added between Pandora
and MediaWiki.

## Open Questions

 * Is all the data currently being delivered by the Mercury API used?
 * Do we need to support partial payloads and transclusion?
 * What should be embedded and what shouldn’t by default. For example, do we
	 always need to include both ‘contributors’ and ‘users’?

## FAQ

 * Will the article HTML content change from what is now in Mercury?
   Yes... and no. We plan to provide a toggle for the render type of the article
   content. See the [phase 1 proposal](design/PHASE-1.md).
   At the moment we are planning on leaving the *values* in the mercury
	 payload intact. The keys however are subject to change.
 * Is Pandora a service or a library?
   Initially Pandora will be a service. However, some of the components
   developed in Pandora may become libraries. For example, HTTP clients for
   MediaWiki and Nirvana will probably be split out as libraries. Similarly,
   abstractions for building services using dropwizard may also be split out.
 * Why don’t aren’t we doing this in the MediaWiki stack?
   Other frameworks and languages have better
   [tooling](https://github.com/Netflix/Hystrix) for [creating
   APIs](http://dropwizard.io/) and distributed systems and we are reluctant to
   invest in adding this to MediaWiki. Also, the 95th percentile on a noop
   controller action in Nirvana is about 65ms[1].


## References
 1. Measured using `ab -n 1000 -c 10 -H 'Host: muppet.wikia.com'  -H 'Cookie: UserId=111' 'http://border-http-s4/wikia.php?controller=MercuryApi&action=foo'`
    from within the datacenter.
