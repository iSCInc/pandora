(ns pandora.gateway.mediawiki.mercury
  (:require [org.httpkit.client :as http]
            [clojure.java.io :as io]
            [cheshire.core :as json]
            [slingshot.slingshot :refer [try+ throw+]])
  (:use [environ.core]))

(def default-article-fixture-dir "data/articles")

(defn get-article-template
  [title]
  (json/parse-string
    (slurp (format "%s/muppet.Kermit_the_Frog.json" (env :article-fixture-dir default-article-fixture-dir)))))


(defn details
  [p]
  (get-in p ["data" "details"]))

(defn title
  [p]
  (get (mercury-map->details p) "title"))

(defn abstract
  [p]
  (get (mercury-map->details p) "abstract"))

(defn url
  [p]
  (get (mercury-map->details p) "url"))
