(ns pandora.gateway.mediawiki-test
  (:require [midje.sweet :refer :all]
            [pandora.gateway.mediawiki :refer [url]]
            [pandora.vars :as vars])
  (:use [environ.core]))

(facts :url
  (url "foobar" "articles/name") => "http://foobar.wikia.com/articles/name"
  (url "foobar" "/articles/name") => "http://foobar.wikia.com/articles/name"
  (provided
    (env :wikia-domain vars/default-wikia-domain) => "wikia.com"))
