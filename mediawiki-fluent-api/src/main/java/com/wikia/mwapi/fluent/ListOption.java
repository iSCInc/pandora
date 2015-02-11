package com.wikia.mwapi.fluent;

import com.wikia.mwapi.fluent.query.AllCategoriesOption;
import com.wikia.mwapi.fluent.query.CategoryMembersOption;

public interface ListOption {

  QueryOption abusefilters();

  QueryOption abuselog();

  AllCategoriesOption allcategories();

  QueryOption alldeletedrevisions();

  QueryOption allfileusages();

  QueryOption allimages();

  QueryOption alllinks();

  QueryOption allpages();

  QueryOption allredirects();

  QueryOption alltransclusions();

  QueryOption allusers();

  QueryOption backlinks();

  QueryOption betafeatures();

  QueryOption blocks();

  CategoryMembersOption categorymembers();

  QueryOption centralnoticelogs();

  QueryOption checkuser();

  QueryOption checkuserlog();

  QueryOption deletedrevs();

  QueryOption embeddedin();

  QueryOption exturlusage();

  QueryOption filearchive();

  QueryOption gadgetcategories();

  QueryOption gadgets();

  QueryOption geosearch();

  QueryOption gettingstartedgetpages();

  QueryOption globalallusers();

  QueryOption globalblocks();

  QueryOption globalgroups();

  QueryOption imageusage();

  QueryOption iwbacklinks();

  QueryOption langbacklinks();

  QueryOption logevents();

  QueryOption mmsites();

  QueryOption oldreviewedpages();

  QueryOption pagepropnames();

  QueryOption pageswithprop();

  QueryOption prefixsearch();

  QueryOption protectedtitles();

  QueryOption querypage();

  QueryOption random();

  QueryOption recentchanges();

  QueryOption search();

  QueryOption tags();

  QueryOption usercontribs();

  QueryOption users();

  QueryOption watchlist();

  QueryOption watchlistraw();

  QueryOption wikigrokrandom();

  QueryOption wikisets();


}
