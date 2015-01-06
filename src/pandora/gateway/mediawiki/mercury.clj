(ns pandora.gateway.mediawiki.mercury
  (:require [org.httpkit.client :as http]
            [clojure.java.io :as io]
            [cheshire.core :as json]
						[cemerick.url :refer [url-encode]]
            [slingshot.slingshot :refer [try+ throw+]]
            [pandora.vars :as vars]
            [pandora.gateway.mediawiki :as mediawiki])
  (:use [environ.core]))

(def default-article-fixture-dir "data/articles")

(defn get-article-template
  [wikia title]
  (json/parse-string
    (slurp (format "%s/muppet.Kermit_the_Frog.json" (env :article-fixture-dir default-article-fixture-dir)))))

(defn get-article!
  "Returns a promise to the request to get the article. When dereferenced contains keys
  [:opts :body :headers :status]."
  [wikia title]
  (http/get (mediawiki/url wikia (format "/api/v1/Mercury/Article?title=%s" (url-encode title)))
            {:timeout vars/mediawiki-request-timeout
             :user-agent vars/pandora-ua}))

(defn resolve-request
  [request]
  (let [resp @request
        success (= (:status resp) 200)]
    {:body (json/parse-string (:body resp))
     :success success}))

(defn details
  [p]
  (get-in p ["data" "details"]))

(defn title
  [p]
  (get (details p) "title"))

(defn id
  [p]
  (get (details p) "id"))

(defn abstract
  [p]
  (get (details p) "abstract"))

(defn url
  [p]
  (get (details p) "url"))

(defn article
  [p]
  (get-in p ["data" "article"]))

(defn content
  [p]
  (get (article p) "content"))
