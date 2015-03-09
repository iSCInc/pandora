# Current Functionality

This document describes the current functionality provided by Mercury.

## Mercury API

This
[request](http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit%20the%20Frog)
provides an example of the payload provided by Mercury. An snapshot of the data provided by the Mercury API can be found
[here](https://gist.github.com/drsnyder/db9649bc0fa2daa2f41e).

## Data Provided

The Merucry API provides the following data.

 * data: This is the top most key in the JSON.
  * details: This element contains the following:
	 * id: the article id
	 * title: the article title
	 * ns: the article name space
	 * url: the relative url for the article
	 * revision: a map containing information about the current revision. This
		 includes the id, user, user_id, and timestamp.
	 * comments: the number of comments
	 * type: the article type? in the request above it’s "article"
	 * abstract: the short article snippet
	 * thumbnail: a thumbnail that represents the article. How is this selected?
	 * original_dimensions: a map with the original width and height of the thumbnail.
	 * metadata: a map with
	  * map_location: a map including the id, latitude and longitude
		* poi_id: point of interest id?
		* poi_category_id:
		* parent_poi_category_id:
  * topContributors: an array of contributors. Each element is a map containing
		the user_id, title, name (same as title), url, numberofedits, and avatar.
		This list appears to be limited to 5.
	* article: a map containing 
	 * content: provides the article content
	 * media: an array or arrays. Each inner array contains a list of maps that
		 include the list of elements described below. The outer array can also
		 contain maps containing the same elements. An example might look like
		 `[ [ map, map ], [ map, map ] map, map, map, map ]`
		 Some work needs to be done to understand the meaning of this structure.
		 * type: e.g. “image”
		 * url: original image URL.
		 * fileUrl: URL of the file object for the image.
		 * title: the name of the file.
		 * caption: a description of the image
		 * user: the user who uploaded it?
		 * width: the width of the original?
		 * height: the height of the original?
	* relatedPages: An array of maps referencing related pages. Each map contains
		the keys url, title, id, imgUrl, imgOriginalDimensions (width and height of
		imgUrl), and text. It’s not clear what the limit to this set is.
	* adsContext: a map containing the following keys: opts (map containing
		metadata), targeting (a map containing inforamtion about the page including
		the name, ategory, article id, the database name, laguage,
		category and several other ‘wiki*’ values), providers (a map), slots (list), and
		forceProviders (list). The targeting map contains several data elements
		that are already provided elsewhere.

Note that the `article/content` element in the current Mercury response has
images elided. This allows for images to be lazy loaded the application running
in the browser as opposed to being downloaded automatically.

This may not be the format required by all applications. Some form of toggle
(e.g. query param) will need to be supported.
