(defproject dawran6/emoji "0.1.5"
  :description "Emoji for Clojure"
  :url "http://github.com/dawran6/emoji"
  :license {:name "MIT"
            :url  "https://github.com/dawran6/emoji/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "0.2.6"]]
  :source-paths ["src"]
  :profiles
  {:dev {:dependencies [[criterium "0.4.5"]]
         :source-paths ["dev"]}})
