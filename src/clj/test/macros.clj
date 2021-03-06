(ns test.macros
  (:require [game.core :as core]))

(defmacro do-game [s & body]
  `(let [~'state ~s
         ~'get-corp (fn [] (:corp @~'state))
         ~'get-runner (fn [] (:runner @~'state))
         ~'refresh (fn [~'card] (core/get-card ~'state ~'card))
         ~'prompt-choice (fn [~'side ~'choice] (core/resolve-prompt ~'state ~'side {:choice (~'refresh ~'choice)}))
         ~'prompt-card (fn [~'side ~'card] (core/resolve-prompt ~'state ~'side {:card (~'refresh ~'card)}))
         ~'prompt-select (fn [~'side ~'card] (core/select ~'state ~'side {:card (~'refresh ~'card)}))
         ~'prompt-is-card? (fn [~'side ~'card]
                             (and (:cid ~'card) (-> @~'state ~'side :prompt first :card :cid)
                                  (= (:cid ~'card) (-> @~'state ~'side :prompt first :card :cid))))
         ~'prompt-is-type? (fn [~'side ~'type]
                             (and ~'type (-> @~'state ~'side :prompt first :prompt-type)
                                  (= ~'type (-> @~'state ~'side :prompt first :prompt-type))))]
     ~@body))
