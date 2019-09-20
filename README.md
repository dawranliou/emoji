# emoji

[![Clojars Project](https://img.shields.io/clojars/v/dawran6/emoji.svg)](https://clojars.org/dawran6/emoji) [![cljdoc badge](https://cljdoc.org/badge/dawran6/emoji)](https://cljdoc.org/d/dawran6/emoji/CURRENT) [![CircleCI](https://circleci.com/gh/dawran6/emoji.svg?style=svg)](https://circleci.com/gh/dawran6/emoji)

Emoji for Clojure

## Usage

```clojure
(require '[emoji.core :as e])

(e/emojify "Clojure is awesome :thumbsup:")
;; => "Clojure is awesome 👍"

(e/demojify "Clojure is awesome 👍")
;; => "Clojure is awesome :thumbsup:"


;; If you want to be really anoyying
(e/emojify-all "Clojure pizza fire thumbsup smile")
;; => "Clojure 🍕 🔥 👍 😄"

```

## License

Copyright © 2019 Daw-Ran Liou

Distributed under the MIT License
