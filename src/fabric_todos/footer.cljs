(ns fabric-todos.footer
  (:require [fabric-todos.fabric :as fab]
            [fabric-todos.state :as state]))

(defn todo-footer []
  (let [remaining (count (remove #(= true (:done %)) (:todos @state/state)))
        label     (str remaining " item" (when-not (= 1 remaining) "s") " left")]
    [:> fab/Stack {:horizontal true :horizontalAlign "space-between"}
     [:> fab/Text label]
     [:> fab/DefaultButton {:onClick state/clear-completed} "Clear Completed"]]))
