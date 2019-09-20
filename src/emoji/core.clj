(ns emoji.core
  "Core functions"
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.string :as string]))

(def emojis
  "Keywordized emoji data from the `emojis.json`"
  (with-open [r (io/reader (io/resource "emojis.json"))]
    (-> (json/read r :key-fn keyword))))

(def emoji
  "Map from `:shortcode` to emoticon"
  (->> emojis
       (map (juxt :emoji :aliases))
       (mapcat (fn [[emoji aliases]] (map (fn [alias] [(keyword alias) emoji]) aliases)))
       (into {})))

(defn ->emoji
  "Convert a `shortcode` string to emoji"
  [shortcode]
  (-> shortcode keyword emoji))

(defn emojify
  [s]
  (string/replace s #"(:)([-+\w]+)(:)" (fn [[_ delimeter_1 alias delimeter_2]] (-> alias ->emoji))))

(comment
  (def pattern #"(:\w+:)")
  (re-find pattern "Clojure :thumbsup:")
  (string/replace "Clojure :thumbsup:" #"(:)(\w+)(:)" (fn [[_ delimeter_1 alias delimeter_2]] (-> alias ->emoji)))
  (emojify "Clojure is awesome :+1:"))
