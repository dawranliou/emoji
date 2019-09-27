(ns user
  (:require
   [emoji.core :as e :refer [emojis]]))

;; perf testing
;; alias-map
(defn alias-map []
  (->> emojis
       (map (juxt identity :aliases))
       (mapcat (fn [[emoji aliases]] (map (fn [alias] [(keyword alias) emoji]) aliases)))
       (into {})))

(defn alias-map-xf []
  (into {} (comp
            (map (juxt :aliases identity))
            (mapcat (fn [[aliases emoji]]
                      (map (fn [alias] [(keyword alias) emoji]) aliases))))
        emojis))

;; unicode-map
(defn unicode-map []
  (->> emojis
       (map (juxt identity :emojiChar))
       (map (fn [[emoji unicode]] [(keyword unicode) emoji]))
       (into {})))

(defn unicode-map-xf []
  (into {} (comp
            (map (juxt identity :emojiChar))
            (map (fn [[emoji unicode]] [(keyword unicode) emoji])))
        emojis))

(comment
  (require '[criterium.core :as criterium])

  (criterium/quick-benchmark* (alias-map))

  "
Evaluation count : 246 in 6 samples of 41 calls.
             Execution time mean : 2.592084 ms
    Execution time std-deviation : 152.224926 µs
   Execution time lower quantile : 2.345529 ms ( 2.5%)
   Execution time upper quantile : 2.748554 ms (97.5%)
                   Overhead used : 10.789885 ns

Found 1 outliers in 6 samples (16.6667 %)
  low-severe	 1 (16.6667 %)
 Variance from outliers : 14.5692 % Variance is moderately inflated by outliers
"

  (criterium/quick-bench (alias-map-xf))

  "
Evaluation count : 246 in 6 samples of 41 calls.
             Execution time mean : 2.737474 ms
    Execution time std-deviation : 108.781029 µs
   Execution time lower quantile : 2.624297 ms ( 2.5%)
   Execution time upper quantile : 2.893353 ms (97.5%)
                   Overhead used : 10.789885 ns

Found 1 outliers in 6 samples (16.6667 %)
  low-severe	 1 (16.6667 %)
 Variance from outliers : 13.8889 % Variance is moderately inflated by outliers
"

  (criterium/quick-bench (unicode-map))
  "
Evaluation count : 732 in 6 samples of 122 calls.
             Execution time mean : 821.710403 µs
    Execution time std-deviation : 22.457106 µs
   Execution time lower quantile : 787.478180 µs ( 2.5%)
   Execution time upper quantile : 841.088796 µs (97.5%)
                   Overhead used : 10.789885 ns
"

  (criterium/quick-bench (unicode-map-xf))
  "
Evaluation count : 558 in 6 samples of 93 calls.
             Execution time mean : 1.051565 ms
    Execution time std-deviation : 27.120888 µs
   Execution time lower quantile : 1.008676 ms ( 2.5%)
   Execution time upper quantile : 1.076938 ms (97.5%)
                   Overhead used : 10.789885 ns
"
  )
