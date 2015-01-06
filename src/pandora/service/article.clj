(ns pandora.service.article
  (:require [pandora.gateway.mediawiki.mercury :as mercury]
            [pandora.domain.article :as article]))

(defn article!
  [wikia article-name]
  (let [resp (mercury/get-article! wikia article-name)
        {body :body success :success} (mercury/resolve-request resp)]
    (when body
      (let [name (mercury/title body)
            headline (mercury/abstract body)
            articleBody (mercury/content body)
            id (mercury/id body)]
        (article/->Article name id headline articleBody (article/url name))))))
