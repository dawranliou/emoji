(ns emoji.core
  "Core functions"
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.string :as string]))

(def emojis
  "Keywordized emoji data from the `resources/emojis.json`"
  (with-open [r (io/reader (io/resource "emojis.json"))]
    (-> (json/read r :key-fn keyword))))

(def ^:private shortcode->emoji
  "Map from `:shortcode` to emoji."
  (->> emojis
       (map (juxt identity :aliases))
       (mapcat (fn [[emoji aliases]] (map (fn [alias] [(keyword alias) emoji]) aliases)))
       (into {})))

(defn ->emoji
  "Convert a `shortcode` string to emoji unicode.

  ```clojure
  (->emoji \"thumbsup\")
  ;; => \"👍\"
  ```"
  [shortcode]
  (-> shortcode keyword shortcode->emoji :emoji))

(defn emojify
  "Replace emoji shortcodes in a string with unicode codes.

  ```clojure
  (emojify \"Clojure is awesome :thumbsup:\")
  ;; => \"Clojure is awesome 👍\"
  ```"
  [s]
  (string/replace s
                  #"(:)([-+\w]+)(:)"
                  (fn [[_ delimeter_1 alias delimeter_2]] (-> alias ->emoji))))

(defn emojify-all
  "Replace every word in a sentence to emoji if it is an alias.

  Be aware. This function might make you very annoying.
  ```clojure
  (emojify-all \"Clojure pizza fire thumbsup smile\")
  ;; => \"Clojure 🍕 🔥 👍 😄\"
  ```"
  [s]
  (->> (string/split s #" ")
       (map #(or (->emoji %) %))
       (string/join " ")))

(comment
  (def pattern #"(:)([-+\w]+)(:)")
  (re-find pattern "Clojure :thumbsup:"))
