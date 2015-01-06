(ns pandora.gateway.mediawiki
  "A set of utilities for interacting with MediaWiki as a service."
  (:require [pandora.vars :as vars]
            [clojure.string :as s])
  (:use [environ.core]))

(defn url
  [wikia uri]
  (let [uri (s/replace uri #"^/" "")]
    (format "http://%s.%s/%s" wikia (env :wikia-domain vars/default-wikia-domain) uri)))
