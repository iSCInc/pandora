# Pandora Phase 1

The primary focus of Phase 1 will be to create a hypermedia API, consistent
with the [API design
guide](https://github.com/Wikia/guidelines/tree/master/APIDesign), that will
provide data parity with the mobile APIs developed by the Mercury project.

## Mercury API

See the [description of the current API](CURRENT.md) for reference.

## Proposed Resources

Below is a proposed resource structure for the API. This list is not
exhaustive.

Top level:
 * Articles
  * `/muppet/articles/Kermit_the_frog`
  * `/muppet/articles/Kermit_the_frog/users/`
  * `/muppet/articles/Kermit_the_frog/media/`
  * `/muppet/articles/Kermit_the_frog/comments/`
  * `/muppet/articles/Kermit_the_frog/categories/`
 * Users
  * `/muppet/users`
  * `/muppet/users/Jbrangwynne53`
 * Comments
  * `/muppet/comments`
  * `/muppet/comments/12345`
 * Categories
  * `/muppet/categories`
  * `/muppet/categories/Muppet Wiki`

Note that sub-pages are missing from the article URI’s above. We’ll need to
find a suitable approach to handling these pages that allows for nested
resources of different types.

See also [PLATFORM-693](https://wikia-inc.atlassian.net/browse/PLATFORM-693)
which is an enumeration of the data required by Mercury.

## Entry Point JSON Example

See [below](https://gist.github.com/drsnyder/e2ed01e670192de3c895) for a
hypothetical JSON representation. This example is not comprehensive.

```JSON
{
  "_links": {
    "self": {
      "href": "/muppet/articles/Kermit the Frog"
    },
    "comments": [
      {
        "href": "/muppet/articles/Kermit the Frog/comments"
      }
    ],
    "users": [
      {
        "href": "/muppet/articles/Kermit the Frog/users"
      }
    ],
    "categories": [
      {
        "href": "/muppet/articles/Kermit the Frog/categories"
      }
    ]
  },
  "title": "Kermit the Frog",
  "id": 50,
  "content": "<div class=\"noprint\"> <div><b>Performer:</b> <div><b><a href=\"/wiki/Jim_Henson\" ...",
  "__embedded": {
    "users": [
      {
        "_links": {
          "self": { "href": "/users/Toughpigs" }
        },
        "user_id": 10370,
        "title": "Toughpigs",
        "name": "Toughpigs",
        "numberofedits": 128537,
        "avatar": "http:\/\/img3.wikia.nocookie.net\/__cb1313595066\/common\/avatars\/thumb\/b\/b5\/10370.png\/100px-10370.png.jpg"
      },
      {
        "_links": {
          "self": { "href": "/users/WikicontributorHubpup" }
        },
        "user_id": 3275812,
        "title": "WikicontributorHubpup",
        "name": "WikicontributorHubpup",
        "numberofedits": 6201,
        "avatar": "http:\/\/img3.wikia.nocookie.net\/__cb0\/messaging\/images\/thumb\/c\/cf\/Avatar6.jpg\/100px-Avatar6.jpg"
      }
    ]
  }
}
```

The `GET` request resulting in this representation might take the following
form
`/muppet/articles/Kermit+the+Frog?embed=[users]&renderType=mobile&mediaWidth=100`.

Some comments about the request and the payload.
 * The `embed=[users]` notation is an example of how we might handle
	 transclusion of data from related resources. The link to the users is also
	 provided in the `_links` section at the top.
 * The `renderType=mobile` is an example of how the render type of the article
	 could be changed.
 * By specifying the `mediaWidth` the caller can toggle the size of the images
   included in the representation. See
	 [PLATFORM-607](https://wikia-inc.atlassian.net/browse/PLATFORM-607) for more
	 information.

## Current Status

For the current status, refer to the [Pandora
project](https://wikia-inc.atlassian.net/secure/RapidBoard.jspa?rapidView=139&quickFilter=1053)
in Jira.
