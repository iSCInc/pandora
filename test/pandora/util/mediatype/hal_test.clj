(ns pandora.util.mediatype.hal-test
  (:require [midje.sweet :refer :all]
            [pandora.domain.article :as article-domain]
            [pandora.util.mediatype.hal :as mt-hal]))

(facts :record->hal-resource
  (let [url "http://wikia.com/Foo bar"
        article (article-domain/->Article "Foo bar" "an abstract" "the body" url)
        resource (mt-hal/record->hal-resource article)]
    (:href resource) => url
    (:properties resource) => (contains {:name "Foo bar" :headline "an abstract" :articleBody "the body"})))
