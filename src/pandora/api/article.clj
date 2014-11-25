(ns pandora.api.article
  (:require [pandora.api.common :refer [create-api-response create-resource]]
            [pandora.service.article :as article-service]
            [pandora.util.mediatype.hal :as domain-hal]))


(defn article
  [name]
  (create-resource (fn [_]
                     (create-api-response
                       (domain-hal/record->hal-resource (article-service/fetch-article! name))))))
