# Pandora Design Document

This document provides an overview of the Pandora design. 

## Goals

 1. Provide an API that satisfies the [design
    guide](https://github.com/Wikia/guidelines/tree/master/APIDesign) for mobile
    web.
 2. Provide an archetype that can be used for other services.
 3. Provide a foundation upon which to expand the service and API to support
    mobile apps and partners.

## Nouns

The content ontology or nouns are being worked out in [this
document](https://docs.google.com/document/d/1N_AFFmdzmjtzTK8g4LOcrC7RdEi9bXy_j-UyihKssTs/edit?usp=sharing).

 * /articles/Kermit_the_frog
 * /articles/Kermit_the_frog/contributors/

## Existing Mercury API

An example query from the current Mercury API can be found
[here](http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit%20the%20Frog).
This will be used as a reference point for mobile web.

## Milestones

 1. `application/hal+json` API for articles
 2. `application/hal+json` API that is feature complete with Mercury and could be
   used as a replacement

### Questions

 * Is all the data currently being delivered by the Mercury API used?
 * Do we need to support partial payloads and transclusion?
 * What should be embedded and what shouldn’t by default. For example, do we
	 always need to include both ‘contributors’ and ‘users’?

## Implementation Notes

 * The Mercury API
   [payload](http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit%20the%20Frog)
	 contains elements that change at different rates which suggests that there might
	 be different logical groupings and different caching characteristics. What
	 are these groupings?
 * Given the above it may also make sense to, by default, only include links to
	 these resources and allow them to be expanded via `_embedded`. This seems to
	 be more the spirit of a hypermedia API.

### Architecture & Data Flow

![Pandora Architecture](assets/pandora-arch.png)

This is similar to how the Mercury service is currently designed. Some of the
content components are called out (comments, media, users as examples) to
suggest that they may be migrated to separate services in the future. The list
is not exhaustive.

To improve performance, a caching layer (varnish) will be added between Pandora
and MediaWiki.

In the near term Pandora will act mostly as a proxy that will

 * Provide a [guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign) compliant content API to clients.
 * Enable rapid prototyping and iteration of the above
 * Decouple content which will enable
  * Hypermedia API designs
  * Better caching
  * Ability to fan-out requests

## FAQ

 * Will the article HTML content change from what is now in Mercury?
   No. At the moment we are planning on leaving the *values* in the mercury
	 payload intact. The keys however are subject to change.
