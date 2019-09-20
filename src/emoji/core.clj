(ns emoji.core
  "Core functions"
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.string :as string]))

(def emojis
  "Keywordized emoji data from the `resources/emojis.json`"
  (with-open [r (io/reader (io/resource "emojis.json"))]
    (-> (json/read r :key-fn keyword))))

(def ^:private alias-map
  "Map from `:alias` to emoji."
  (->> emojis
       (map (juxt identity :aliases))
       (mapcat (fn [[emoji aliases]] (map (fn [alias] [(keyword alias) emoji]) aliases)))
       (into {})))

(defn ->emoji
  "Convert a `alias` string to emoji unicode.

  ```clojure
  (->emoji \"thumbsup\")
  ;; => \"👍\"
  ```"
  [alias]
  (-> alias keyword alias-map :emoji))

;;
;; Emojify
;;

(defn emojify
  "Replace emoji aliases in a string with unicode codes.

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

;;
;; Demojify
;;

(def ^:private regex-char-esc-smap
  ;; Stole frome https://stackoverflow.com/a/11672480
  (let [esc-chars "()*&^%$#!"]
    (zipmap esc-chars
            (map #(str "\\" %) esc-chars))))

(defn- escape-regex-char
  ;; Stole frome https://stackoverflow.com/a/11672480
  [string]
  (->> string
       (replace regex-char-esc-smap)
       (apply str)))

(def ^:private emoji-unicode-pattern
  (let [emojis-by-length (->> emojis
                              (map :emojiChar)
                              (sort-by count #(compare %2 %1)))
        regex-str        (->> emojis-by-length
                              (map escape-regex-char)
                              (string/join "|"))]
    (re-pattern (str "(" regex-str ")"))))

(def ^:private unicode-map
  (->> emojis
       (map (juxt identity :emojiChar))
       (map (fn [[emoji unicode]] [(keyword unicode) emoji]))
       (into {})))

(defn ->alias
  "Convert a `unicode` string to emoji shortcode/alias.

  ```clojure
  (->alias \"👍\")
  ;; => \"thumbsup\"
  ```"
  [unicode]
  (let [emoji (-> unicode keyword unicode-map)]
    (str ":" (last (:aliases emoji)) ":")))

(defn demojify
  "Replace emoji unicodes in a string with shortcodes/aliases.

  ```clojure
  (emojify \"Clojure is awesome 👍\")
  ;; => \"Clojure is awesome :thumbsup:\"
  ```"
  [s]
  (string/replace s
                  emoji-unicode-pattern
                  (fn [[_ unicode]] (->alias unicode))))

(comment
  (def pattern #"(:)([-+\w]+)(:)")
  (re-find pattern "Clojure :thumbsup:"))
