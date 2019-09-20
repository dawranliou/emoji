(ns emoji.core-test
  (:require [clojure.test :refer :all]
            [emoji.core :as emoji]))

(deftest ->emoji-test
  (testing "String to emoji"
    (is (= (emoji/->emoji "smile")
           "😄"))))

(deftest emojify-test
  (testing "Sentence with emojis"
    (is (= (emoji/emojify "Clojure is awesome :thumbsup:")
           "Clojure is awesome 👍"))
    (is (= (emoji/emojify "Sending :e-mail:...")
           "Sending 📧..."))))

(deftest emojify-all-test
  (testing "Regular sentence with no intention to use emoji"
    (is (= (emoji/emojify-all "Sending e-mail with a smile")
           "Sending 📧 with 🅰 😄"))))
