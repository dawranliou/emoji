(ns user)

(require '[clj-http.client :as client])
(require '[clojure.data.json :as json])

(def emojis (->
             (client/get "https://raw.githubusercontent.com/omnidan/node-emoji/master/lib/emoji.json")
             :body
             (json/read-str)
             (into {})))

(def sorted-emojis
  (into (sorted-map-by (fn [k1 k2] (compare [(get emojis k1) k1] [(get emojis k2) k2]))) emojis))

(comment
  (spit "resources/emoji.edn" sorted-emojis)
  (take 10 sorted-emojis))
