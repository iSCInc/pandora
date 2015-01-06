(ns pandora.http.routes-test
  (:require [midje.sweet :refer :all]
            [cheshire.core :refer :all]
						[ring.mock.request :refer :all]
            [pandora.vars :as vars]
            [pandora.gateway.mediawiki.mercury :as mercury]
            [pandora.api.article :as article-api]
            [pandora.service.article :as article-service]  
            [pandora.http.routes :as r]))

(facts :articles
  (let [a-request (-> (request :get "/muppet/articles/foo")
                      (header "Accept" vars/hal+json))]
    (r/app-routes a-request) => (contains {:headers map? :body string? :status integer?})
    ;(:status (r/app-routes a-request)) => 200
    ;(get-in (r/app-routes a-request) [:headers "Content-Type"]) => (contains vars/hal+json)  
    (provided
      (mercury/get-article! "muppet" "foo") => (mercury/get-article-template "muppet" "foo"))))

(facts :articles :no-acceptable
  (let [a-request (-> (request :get "/muppet/articles/foo")
                      (header "Accept" "application/something"))]
    (:status (r/app-routes a-request)) => 406))
