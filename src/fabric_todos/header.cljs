(ns fabric-todos.header
  (:require [clojure.string :refer [blank?]]
            [fabric-todos.fabric :as fab]
            [fabric-todos.state :as state]))

(defn focus-new-todo []
  (.focus (.getElementById js/document "newTodo")))

(defn pivot-filter [pivot-item]
  (state/update-filter pivot-item.props.headerText))

(defn textfield-change [event newValue]
  (swap! state/state assoc :labelInput newValue)
  (when state/debug (println @state/state)))

(defn add-btn-handler []
  (let [text (get @state/state :labelInput)]
    (when-not (blank? text)
      (state/add-todo text)
      (swap! state/state assoc :labelInput "")
      (focus-new-todo)
      (when state/debug (println @state/state)))))

(defn todo-header []
  [:> fab/Stack
   [:> fab/Stack {:horizontal true :horizontalAlign "center"}
    [:> fab/Stack.Item {:align "center"}
     [:> fab/Text {:variant "xxLarge"} "todos"]]]
   [:> fab/Stack {:horizontal "horizontal"}
    [:> fab/Stack.Item {:grow true}
     [:> fab/TextField {:id "newTodo"
                        :placeholder "What needs to be done?"
                        :value (:labelInput @state/state)
                        :onKeyDown #(when (= 13 (.-which %)) (add-btn-handler))
                        :onChange textfield-change}]]
    [:> fab/PrimaryButton {:onClick add-btn-handler} "Add"]]
   [:> fab/Pivot {:onLinkClick pivot-filter}
    [:> fab/PivotItem {:headerText "all"}]
    [:> fab/PivotItem {:headerText "active"}]
    [:> fab/PivotItem {:headerText "completed"}]]])
