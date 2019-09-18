(ns emoji.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def emoji
  (edn/read (java.io.PushbackReader. (io/reader (io/resource "emoji.edn")))))

(defn ->emoji
  [shortcode]
  (-> shortcode keyword emoji))
