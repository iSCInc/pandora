(ns pandora.util.mediatype.hal
  (:require [halresource.resource :as hal]
            [plumbing.core :refer [safe-get]]
            [pandora.vars :as vars]))


(defn record->hal-resource
  [record]
  {:pre [(map? record)]
   :post [(map? %)]}
  (let [resource (hal/new-resource (safe-get record :url))]
    (reduce (fn [rs k]
              (hal/add-property rs k (get record k)))
            resource
            (filter #(not= % :url) (keys record)))))
