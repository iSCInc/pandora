(ns pandora.domain.article
  (:require [pandora.vars :as vars])
  (:use [environ.core]))

(defn url
  [name]
  (format "%s/articles/%s" vars/service-address name))

(defrecord Article [name headline articleBody url])
