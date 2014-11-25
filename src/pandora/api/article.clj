(ns pandora.api.article
  (:require [pandora.api.common :refer [create-api-response create-resource]]
            [pandora.service.article :as article-service]))

(defn articles
  "What operations are possible against /articles/"
  [input])

(defn article
  "Fetch an article by name."
  [name]
  (create-resource (fn [ctx]
                     (create-api-response
                       (article-service/fetch-article! name)))))
