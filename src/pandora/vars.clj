(ns pandora.vars
  (:use [environ.core])
  (:import [java.net InetAddress]))

(def default-port 8080)
(def service-address (env :service-address (format "http://localhost:%d" default-port)))
(def hostname (.getHostName (InetAddress/getLocalHost)))


; media types
(def hal+json "application/hal+json")
