(ns user
  (:require [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [clojure.tools.trace :refer :all]
            [clout.core :as c]
            [midje.repl :refer :all]
            [ring.mock.request :refer :all]
            [wikia.common.logger :as log])
  (:use [environ.core]))
