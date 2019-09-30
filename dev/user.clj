(ns user
  (:require
   [emoji.core :as e :refer [emojis]]))

;; perf testing
;; alias-map
(defn alias-map []
  (->> emojis
       (mapcat (fn [{:keys [aliases] :as emoji}]
                 (map (fn [alias] [(keyword alias) emoji]) aliases)))
       (into {})))

(defn alias-map-xf []
  (into {}
        (mapcat (fn [{:keys [aliases] :as emoji}]
                  (map (fn [alias] [(keyword alias) emoji])
                       aliases)))
        emojis))

;; unicode-map
(defn unicode-map []
  (->> emojis
       (map (fn [{:keys [emojiChar] :as emoji}] [(keyword emojiChar) emoji]))
       (into {})))

(defn unicode-map-xf []
  (into {}
        (map (fn [{:keys [emojiChar] :as emoji}] [(keyword emojiChar) emoji]))
        emojis))

(comment
  (= (alias-map) (alias-map-xf))
  (= (unicode-map) (unicode-map-xf))

  (require '[criterium.core :as criterium])

  (criterium/quick-bench (alias-map))

  "
Evaluation count : 294 in 6 samples of 49 calls.
             Execution time mean : 2.148035 ms
    Execution time std-deviation : 48.784635 µs
   Execution time lower quantile : 2.090944 ms ( 2.5%)
   Execution time upper quantile : 2.203614 ms (97.5%)
                   Overhead used : 14.743842 ns
"

  (criterium/quick-bench (alias-map-xf))

  "
Evaluation count : 336 in 6 samples of 56 calls.
             Execution time mean : 1.870553 ms
    Execution time std-deviation : 30.348597 µs
   Execution time lower quantile : 1.835876 ms ( 2.5%)
   Execution time upper quantile : 1.913168 ms (97.5%)
                   Overhead used : 14.743842 ns
"

  (criterium/quick-bench (unicode-map))

  "
Evaluation count : 894 in 6 samples of 149 calls.
             Execution time mean : 706.637236 µs
    Execution time std-deviation : 17.400058 µs
   Execution time lower quantile : 688.468638 µs ( 2.5%)
   Execution time upper quantile : 725.721997 µs (97.5%)
                   Overhead used : 14.743842 ns
"

  (criterium/quick-bench (unicode-map-xf))

  "
Evaluation count : 882 in 6 samples of 147 calls.
             Execution time mean : 705.239297 µs
    Execution time std-deviation : 19.030340 µs
   Execution time lower quantile : 680.384612 µs ( 2.5%)
   Execution time upper quantile : 721.176868 µs (97.5%)
                   Overhead used : 14.743842 ns
"
  )
