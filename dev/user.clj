(ns user
  (:require [cheshire.core :refer :all]
            [clojure.java.io :as io]
						[org.httpkit.server :refer :all]
            [clojure.tools.trace :refer :all]
						[compojure.core :refer :all]
            [clout.core :as c]
            [midje.repl :refer :all]
            [ring.mock.request :refer :all]
						[ring.middleware.reload :as reload]
            [wikia.common.logger :as log]
						[pandora.http.routes :as r])
  (:use [environ.core]))

(def default-port 8080)

(def server (atom nil))

(defn run
  ([app system port]
   (run-server
     (app system)
     {:port port}))
  ([app system]
   (run app system default-port)))

(defn start
	([port]
	 (swap! server (fn [_]
									 (run-server (reload/wrap-reload #'r/app-routes) {:port port}))))
	([]
	 (start default-port)))

(defn stop
	[]
	(when @server
		(@server)))
