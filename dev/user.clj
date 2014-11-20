(ns user
	(:require [cheshire.core :refer :all]
						[halresource.resource :as hal]
						[clojure.java.io :as io]
						[org.httpkit.server :refer :all]
						[clojure.tools.trace :refer :all]
						[compojure.core :refer :all]
						[clout.core :as c]
						[midje.repl :refer :all]
						[ring.mock.request :refer :all]
						[ring.middleware.reload :as reload]
						[wikia.common.logger :as log]
						[pandora.http.routes :as r]
						[pandora.gateway.mediawiki.mercury :as mercury]
						[pandora.vars :as vars])
  (:use [environ.core]))

(def server (atom nil))

(defn run
  ([app system port]
   (run-server
     (app system)
     {:port port}))
  ([app system]
   (run app system vars/default-port)))

(defn start
	([port]
	 (swap! server (fn [_]
									 (run-server (reload/wrap-reload #'r/app-routes) {:port port}))))
	([]
	 (start vars/default-port)))

(defn stop
	[]
	(when @server
		(@server)))
