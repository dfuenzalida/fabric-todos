(ns fabric-todos.todo-list
  (:require [fabric-todos.fabric :as fab]
            [fabric-todos.state :as state]))

(defn todo-item [{:keys [id text editing done] :as item}]
  [:div {:key (str "stack" id)}
   [:> fab/Stack {:horizontal true :horizontalAlign "space-between" :verticalAlign "center" }
    (if editing
      [:> fab/Stack.Item {:grow true}
       [:> fab/Stack {:horizontal "horizontal"}
        [:> fab/Stack.Item {:grow true}
         [:> fab/TextField {:value text}]]
        [:> fab/DefaultButton {:onClick #(state/update-todo id)} "save"]]]
      ;; else
      [:> fab/Stack.Item {:grow true}
       [:> fab/Stack {:horizontal true}
        [:> fab/Stack.Item {:grow true}
         [:> fab/Checkbox {:label text :checked done :onChange #(state/toggle-done id)}]]
        [:> fab/Stack
         [:> fab/IconButton {:iconProps {:iconName "Edit"} :className "clearButton" :onClick #(state/edit-todo id)}]]
        [:> fab/IconButton {:iconProps {:iconName "Cancel"} :className "clearButton" :onClick #(state/delete-todo id)}]]
       ]
      )]])

(defn todo-list []
  (let [remove-map {"all" nil "active" true "completed" false}
        remove-on  (get remove-map (:filter @state/state))
        filtered-todos (remove #(= remove-on (:done %)) (:todos @state/state))]
    [:> fab/Stack {:gap 10}
     (map todo-item filtered-todos)]))

