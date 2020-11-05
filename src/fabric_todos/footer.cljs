(ns fabric-todos.footer
  (:require ["@fluentui/react" :as f]
            [fabric-todos.state :as state]))

(defn todo-footer []
  (let [remaining (count (state/remaining-todos))
        label     (str remaining " item" (when-not (= 1 remaining) "s") " left")]
    [:> f/Stack {:horizontal true :horizontalAlign "space-between"}
     [:> f/Text label]
     [:> f/DefaultButton {:onClick state/clear-completed} "Clear Completed"]]))
