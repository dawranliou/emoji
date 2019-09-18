(ns emoji.core-test
  (:require [clojure.test :refer :all]
            [emoji.core :as emoji]))

(deftest a-test
  (testing "String to emoji"
    (is (= (emoji/->emoji "smile")
           "😄"))))
