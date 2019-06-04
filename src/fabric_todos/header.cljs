(ns fabric-todos.header
  (:require [clojure.string :refer [blank?]]
            [fabric-todos.fabric :as fab]
            [fabric-todos.state :as state]))

(defn focus-new-todo []
  (.focus (.getElementById js/document "newTodo")))

(defn pivot-filter [pivot-item]
  (state/update-filter pivot-item.props.headerText))

(defn textfield-change [event new-value]
  (state/update-label-input new-value))

(defn add-btn-handler []
  (let [text (get @state/state :labelInput)]
    (when-not (blank? text)
      (state/add-todo text)
      (state/update-label-input "")
      (focus-new-todo))))

(defn todo-header []
  [:> fab/Stack
   [:> fab/Stack {:horizontal true :horizontalAlign "center"}
    [:> fab/Stack.Item {:align "center"}
     [:> fab/Text {:variant "xxLarge"} "todos"]]]
   [:> fab/Stack {:horizontal "horizontal"}
    [:> fab/Stack.Item {:grow true}
     [:> fab/TextField {:id "newTodo"
                        :placeholder "What needs to be done?"
                        :value (state/new-todo-value)
                        :onKeyDown #(when (= 13 (.-which %)) (add-btn-handler))
                        :onChange textfield-change}]]
    [:> fab/PrimaryButton {:onClick add-btn-handler} "Add"]]
   [:> fab/Pivot {:onLinkClick pivot-filter}
    [:> fab/PivotItem {:headerText "all"}]
    [:> fab/PivotItem {:headerText "active"}]
    [:> fab/PivotItem {:headerText "completed"}]]])
