(ns pandora.gateway.mediawiki.mercury
  (:require [org.httpkit.client :as http]
            [clojure.java.io :as io]
            [cheshire.core :as json]
            [slingshot.slingshot :refer [try+ throw+]])
  (:use [environ.core]))

(def default-article-fixture-dir "data/articles")

(defn article
  [title]
  (json/parse-string
    (slurp (format "%s/muppet.Kermit_the_Frog.json" (env :article-fixture-dir default-article-fixture-dir)))))
