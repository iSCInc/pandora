(ns pandora.http.routes-test
  (:require [midje.sweet :refer :all]
            [cheshire.core :refer :all]
						[ring.mock.request :refer :all]
            [pandora.vars :as vars]
            [pandora.http.routes :as r]))

(facts :articles
  (let [a-request (-> (request :get "/articles/foo")
                      (header "Accept" vars/hal+json))
        response (r/app-routes a-request)]
    response => (contains {:headers map? :body string? :status integer?})
    (:status response) => 200
    (get-in response [:headers "Content-Type"]) => (contains vars/hal+json)))

(facts :articles :no-acceptable
  (let [a-request (-> (request :get "/articles/foo")
                      (header "Accept" "application/something"))
        response (r/app-routes a-request)]
    (:status response) => 406))
