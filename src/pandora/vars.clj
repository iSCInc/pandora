(ns pandora.vars
  (:use [environ.core]))

(def default-port 8080)
(def service-address (env :service-address (format "http://localhost:%d" default-port)))
