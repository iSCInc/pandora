(ns pandora.api.article-test
  (:require [midje.sweet :refer :all]
            [cheshire.core :refer :all]
						[ring.mock.request :refer :all]
            [pandora.vars :as vars]
            [pandora.api.article :as article-api]
            [pandora.util.mediatype.hal :as mt-hal]))

(facts :article
  (let [a-request (-> (request :get "/articles/foo")
                    (header "Accept" vars/hal+json))
        response ((article-api/article "muppet" "foo") a-request)]
    response => (contains {:headers map? :body string? :status integer?})
    (parse-string (:body response)) => (contains {"_links" vector?})))
