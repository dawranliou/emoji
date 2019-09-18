(ns emoji.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def emoji
  "Map from `:shortcode` to emoticon"
  (edn/read (java.io.PushbackReader. (io/reader (io/resource "emoji.edn")))))

(defn ->emoji
  "Convert a `shortcode` string to emoji"
  [shortcode]
  (-> shortcode keyword emoji))
