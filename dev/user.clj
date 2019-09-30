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

  (criterium/quick-bench (alias-map))

  "
Evaluation count : 276 in 6 samples of 46 calls.
             Execution time mean : 2.476903 ms
    Execution time std-deviation : 437.895254 µs
   Execution time lower quantile : 2.188587 ms ( 2.5%)
   Execution time upper quantile : 3.233494 ms (97.5%)
                   Overhead used : 14.743842 ns

Found 1 outliers in 6 samples (16.6667 %)
  low-severe	 1 (16.6667 %)
 Variance from outliers : 47.9170 % Variance is moderately inflated by outliers
"

  (criterium/quick-bench (alias-map-xf))

  "
Evaluation count : 288 in 6 samples of 48 calls.
             Execution time mean : 2.173080 ms
    Execution time std-deviation : 84.752751 µs
   Execution time lower quantile : 2.092765 ms ( 2.5%)
   Execution time upper quantile : 2.306046 ms (97.5%)
                   Overhead used : 14.743842 ns

Found 1 outliers in 6 samples (16.6667 %)
  low-severe	 1 (16.6667 %)
 Variance from outliers : 13.8889 % Variance is moderately inflated by outliers
"

  (criterium/quick-bench (unicode-map))
  "
Evaluation count : 774 in 6 samples of 129 calls.
             Execution time mean : 776.329331 µs
    Execution time std-deviation : 28.472004 µs
   Execution time lower quantile : 753.820225 µs ( 2.5%)
   Execution time upper quantile : 820.779251 µs (97.5%)
                   Overhead used : 14.743842 ns

Found 1 outliers in 6 samples (16.6667 %)
  low-severe	 1 (16.6667 %)
 Variance from outliers : 13.8889 % Variance is moderately inflated by outliers
"

  (criterium/quick-bench (unicode-map-xf))
  "
Evaluation count : 660 in 6 samples of 110 calls.
             Execution time mean : 963.803505 µs
    Execution time std-deviation : 53.873523 µs
   Execution time lower quantile : 908.428118 µs ( 2.5%)
   Execution time upper quantile : 1.026342 ms (97.5%)
                   Overhead used : 14.743842 ns
"
  )
