(ns user
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]))

(def emojis
  (with-open [r (io/reader (io/resource "emojis.json"))]
    (-> (json/read r :key-fn keyword))))

(->> emojis
     (map (juxt :emoji :aliases))
     (mapcat (fn [[emoji aliases]] (map (fn [alias] [alias emoji]) aliases))))

(comment
  ;; some emojis has multiple aliases
  (->> emojis
       (group-by #(-> % :aliases count))
       keys) ;; => (1 2 3 ...)
  )
