(ns user
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [emoji.core :as e]))

(->> e/emojis
     (map (juxt :emoji :aliases))
     (mapcat (fn [[emoji aliases]] (map (fn [alias] [alias emoji]) aliases))))

(comment
  ;; some emojis has multiple aliases
  (->> e/emojis
       (group-by #(-> % :aliases count))
       keys) ;; => (1 2 3 ...)
  )
