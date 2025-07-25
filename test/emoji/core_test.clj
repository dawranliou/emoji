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

(deftest emojify-test-with-invalid-alias-present
  (testing "Sentence with characters that match the emoji alias pattern but do not represent an emoji"
    (is (= (emoji/emojify ":package: Package was delivered at 2025-06-26T23:21:00.000Z")
           "📦 Package was delivered at 2025-06-26T23:21:00.000Z"))))

(deftest emojify-all-test
  (testing "Regular sentence with no intention to use emoji"
    (is (= (emoji/emojify-all "Sending e-mail with a smile")
           "Sending 📧 with 🅰 😄"))))

(deftest demojify-test
  (testing "Sentence with uinicode emojis"
    (is (= (emoji/demojify "Clojure is awesome 👍")
           "Clojure is awesome :thumbsup:"))
    (is (= (emoji/demojify "Sending 📧...")
           "Sending :e-mail:..."))))
