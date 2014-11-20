(ns pandora.util.mediatype.hal
  (:require [halresource.resource :as hal]
            [pandora.vars :as vars]
            [pandora.domain.article :as article]))


; should this be polymorphic on the type?
(defn article->hal-resource
  [article-record]
  (let [resource (hal/new-resource (:url article-record))]
    (-> resource
        (hal/add-properties {:name (:name article-record)
                             :headline (:headline article-record)
                             :articleBody (:articleBody article-record)}))))
