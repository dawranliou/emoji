(ns user)

(require '[clj-http.client :as client])
(require '[clojure.data.json :as json])

(def emojis (->
             (client/get "https://raw.githubusercontent.com/omnidan/node-emoji/master/lib/emoji.json")
             :body
             (json/read-str)
             (into {})))

(spit "resources/emoji.edn" emojis)
