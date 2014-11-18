(defproject pandora "0.1.0-SNAPSHOT"
  :description "Wikia API."
  :url "http://github.com/Wikia/pandora"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [cheshire "5.3.1"]
                 [wikia/commons "0.1.0-SNAPSHOT"]
                 [clout "1.2.0"]
                 [compojure "1.1.8"]
                 [environ "0.5.0"]
                 [http-kit "2.1.18"]
                 [org.clojure/tools.cli "0.3.1"]
                 [ring "1.3.0"]
                 [slingshot "0.10.3"]]
  :profiles {:dev {:source-paths ["dev"]
                   :plugins [[lein-midje "3.1.1"]]
                   :dependencies  [[midje "1.6.3"]
                                   [javax.servlet/servlet-api "2.5"]
                                   [org.clojure/tools.namespace "0.2.5"]
                                   [org.clojure/tools.trace "0.7.8"]
                                   [ring-mock "0.1.5"]]}}
  :repl-options {:init-ns user})
