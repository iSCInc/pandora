(ns pandora.http.routes
  (:require [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [clout.core :refer [route-compile route-matches]]
            [compojure.core :refer [routes GET ANY]]
            [compojure.route :refer [files not-found]]
            [liberator.core :refer  [resource defresource]]
            [environ.core :refer [env]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response status charset header]]
            [slingshot.slingshot :refer [try+ throw+]]
            [wikia.common.logger :as log])
  (:import [java.net InetAddress]))

(def hostname (.getHostName (InetAddress/getLocalHost)))

(def name-regex #"[\w()-]+")

(def article-route (route-compile "/articles/:name"
                                  {:name name-regex}))

(defn add-headers
  [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (header "Varnish-Logs" "pandora")
          (header "X-Served-By" hostname)
          (header "X-Cache" "ORIGIN")
          (header "X-Cache-Hits" "ORIGIN")
          (header "Connection" "close")))))


(defresource article
  [name]
  :available-media-types ["application/hal+json"]
  :handle-ok (fn [_] (format "the article name was %s\n" name)))

(def app-routes
  (-> (routes
        (GET "/articles/:name" [name]
             (article name))
       (not-found "Unrecognized request path!\n"))
      (add-headers)))
