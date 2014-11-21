(ns pandora.http.routes
  (:require [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [clout.core :refer [route-compile route-matches]]
            [compojure.core :refer [routes GET ANY]]
            [compojure.route :refer [files not-found]]
            [liberator.core :refer  [resource defresource]]
            [liberator.representation :refer [as-response render-map-generic]]
            [environ.core :refer [env]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response status charset header]]
            [slingshot.slingshot :refer [try+ throw+]]
            [pandora.service.article :as article-service]
            [pandora.util.mediatype.hal :as domain-hal]
            [pandora.vars :as vars]
            [halresource.resource :as hal]
            [wikia.common.logger :as log]))

(def hal+json "application/hal+json")

; Liberator
(defmethod render-map-generic hal+json [data context]
  (hal/resource->representation (:body data) :json))

(defn add-headers
  [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (header "Varnish-Logs" "pandora")
          (header "X-Served-By" vars/hostname)
          (header "X-Cache" "ORIGIN")
          (header "X-Cache-Hits" "ORIGIN")
          (header "Connection" "close")))))


(defresource article
  [name]
  :available-media-types [hal+json]
  :handle-ok (fn [_] {:media-type hal+json
                      :body (domain-hal/record->hal-resource (article-service/fetch-article! name))}))

(def app-routes
  (-> (routes
        (GET "/articles/:name" [name]
             (article name))
       (not-found "Unrecognized request path!\n"))
      (add-headers)))
