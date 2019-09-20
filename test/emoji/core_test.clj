(ns emoji.core-test
  (:require [clojure.test :refer :all]
            [emoji.core :as emoji]))

(deftest ->emoji-test
  (testing "String to emoji"
    (is (= (emoji/->emoji "smile")
           "😄"))))

(deftest emoji-test
  (testing "keyword to emoji"
    (is (= (emoji/->emoji :smile)
           "😄"))))
