# Pandora Phase 1

The primary focus of Phase 1 will be to create a hypermedia API, consistent
with the [API design
guide](https://github.com/Wikia/guidelines/tree/master/APIDesign), that will
provide data parity with the mobile APIs developed by the Mercury project.

## Mercury API

See the [description of the current API](design/CURRENT.md) for reference.

## Proposed Resources

Below is a proposed resource structure for the API. This list is not
exhaustive.

Top level:
 * Articles
  * /muppet/articles/Kermit_the_frog
  * /muppet/articles/Kermit_the_frog/users/
  * /muppet/articles/Kermit_the_frog/media/
  * /muppet/articles/Kermit_the_frog/comments/
  * /muppet/articles/Kermit_the_frog/categories/
 * Users
  * /muppet/users
  * /muppet/users/Jbrangwynne53
 * Comments
  * /muppet/comments
  * /muppet/comments/12345
 * Categories
  * /muppet/categories
  * /muppet/categories/Muppet Wiki

Note that sub-pages are missing from the article URI’s above. We’ll need to
find a suitable approach to handling these pages that allows for nested
resources of different types.

See also [PLATFORM-693](https://wikia-inc.atlassian.net/browse/PLATFORM-693)
which is an enumeration of the data required by Mercury.

## Entry Point JSON Example

See [this gist](https://gist.github.com/drsnyder/e2ed01e670192de3c895) for a
hypothetical JSON representation. This example is not comprehensive.

The `GET` request resulting in this representation might take the following
form `/muppet/articles/Kermit+the+Frog?embed=[users]&renderType=mobile`.

## Current Status

For the current status, refer to the [Pandora
project](https://wikia-inc.atlassian.net/secure/RapidBoard.jspa?rapidView=139&quickFilter=1053)
in Jira.
