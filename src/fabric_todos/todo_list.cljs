(ns fabric-todos.todo-list
  (:require [fabric-todos.fabric :as fab]
            [fabric-todos.state :as state]
            [reagent.core :refer [atom]]))

(defn todo-item [{:keys [id text editing done] :as item}]
  [:div {:key (str "stack" id)}
   [:> fab/Stack {:horizontal true :horizontalAlign "space-between" :verticalAlign "center"}
    (if editing
      (let [text-value (atom text)]
        [:> fab/Stack.Item {:grow true}
         [:> fab/Stack {:horizontal true}
          [:> fab/Stack.Item {:grow true}
           [:> fab/TextField {:value @text-value
                              :onChange (fn [ev val] (reset! text-value val))}]]
          [:> fab/DefaultButton {:onClick #(state/update-todo id @text-value)} "save"]]])
      ;; else
      [:<>
       [:> fab/Checkbox {:label text :checked done :onChange #(state/toggle-done id)}]
       [:div
        [:> fab/IconButton {:iconProps {:iconName "Edit"} :className "clearButton" :onClick #(state/edit-todo id)}]
        [:> fab/IconButton {:iconProps {:iconName "Cancel"} :className "clearButton" :onClick #(state/delete-todo id)}]]])
    ]])

(defn todo-list []
  [:> fab/Stack {:gap 10}
   (map todo-item (state/filtered-todos))])

