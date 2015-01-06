(ns pandora.vars
  (:use [environ.core])
  (:import [java.net InetAddress]))

(def pandora-version "1.0")

(def default-port 8080)
(def service-address (env :service-address (format "http://localhost:%d" default-port)))
(def hostname (.getHostName (InetAddress/getLocalHost)))


(def default-wikia-domain "wikia.com")
(def default-mediawiki-request-timeout 500)
(def mediawiki-request-timeout (Integer. (env :mediawiki-request-timeout default-mediawiki-request-timeout)))

; media types
(def hal+json "application/hal+json")

(def pandora-ua (format "pandora-%s" pandora-version))
