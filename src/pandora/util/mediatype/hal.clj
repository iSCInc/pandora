(ns pandora.util.mediatype.hal
  (:require [halresource.resource :as hal]
            [pandora.vars :as vars]
            [pandora.domain.article :as article]))


(defn article->json
  [article-record]
  (let [resource (hal/new-resource (:url article-record))]
    (-> resource
        (hal/add-properties {:name (:name article-record)
                             :headline (:headline article-record)
                             :articleBody (:articleBody article-record)})
        (hal/resource->representation :json))))
