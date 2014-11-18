# Pandora Design Document

This document provides an overview of the Pandora design. 

## Goals

 # Provide an API that satisfies the [design
 	 guide](https://github.com/Wikia/guidelines/tree/master/APIDesign) for mobile
 	 web.
 # Provide an archetype that can be used for other services.
 # Provide a foundation upon which to expand the service and API to support
   mobile apps and partners. 

## Nouns

The content ontology or nouns are being worked out in [this
document](https://docs.google.com/document/d/1N_AFFmdzmjtzTK8g4LOcrC7RdEi9bXy_j-UyihKssTs/edit?usp=sharing).

 * /articles/Kermit_the_frog
 * /articles/Kermit_the_frog/contributors/

## Existing Mercury API

An example query from the current Mercury API can be found
[here](http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit%20the%20Frog).

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
 
### Code Layout

 * `pandora.api.*`: API request handlers live here.
 * `pandora.domain.*`: Pure domain logic belongs here.
 * `pandora.gateway.*`: External service client code belongs here.
