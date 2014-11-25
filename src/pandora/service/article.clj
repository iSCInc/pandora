(ns pandora.service.article
  (:require [pandora.gateway.mediawiki.mercury :as mercury]
            [pandora.domain.article :as article]))

(defn fetch-article!
  [article-name]
  (let [resp (mercury/get-article-template article-name)]
    (when resp
      (let [name (mercury/title resp)
            headline (mercury/abstract resp)
            articleBody (mercury/content resp)
            id (mercury/id resp)]
        (article/->Article name id headline articleBody (article/url name))))))
