(ns pandora.http.routes
  (:require [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [clout.core :refer [route-compile route-matches]]
            [compojure.core :refer [routes GET ANY]]
            [compojure.route :refer [files not-found]]
            [environ.core :refer [env]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response status charset header]]
            [slingshot.slingshot :refer [try+ throw+]]
            [pandora.api.common :refer :all]
            [pandora.api.article :as article-api]
            [pandora.util.mediatype.hal :as domain-hal]
            [pandora.vars :as vars]
            [wikia.common.logger :as log]))


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

(def app-routes
  (-> (routes
        ; TODO: regex check these as best we can
        (GET "/:wikia/articles/:name" [wikia name]
             (article-api/article wikia name))
       (not-found "Unrecognized request path!\n"))
      (add-headers)))
