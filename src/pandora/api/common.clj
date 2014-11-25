(ns pandora.api.common
  (:require [pandora.vars :as vars]
            [halresource.resource :as hal]
            [liberator.core :refer  [resource defresource]]
            [liberator.representation :refer [render-map-generic]]))


(defn create-api-response
  ([media-type body]
   {:media-type media-type :body body})
  ([body]
   (create-api-response vars/hal+json body)))

(defn create-resource
  [ok-handler]
  (resource :available-media-types [vars/hal+json]
            :handle-ok ok-handler))

; Liberator; I'm not a fan of this :body map querying. Is there a
; schema enabled way of doing this? Should it be a protocol?
(defmethod render-map-generic vars/hal+json [data context]
  (hal/resource->representation (:body data) :json))
