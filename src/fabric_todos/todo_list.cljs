(ns fabric-todos.todo-list
  (:require ["@fluentui/react" :as f]
            [fabric-todos.state :as state]
            [reagent.core :refer [atom]]))

(defn todo-item [{:keys [id text editing done] :as item}]
  [:div {:key (str "stack" id)}
   [:> f/Stack {:horizontal true :horizontalAlign "space-between" :verticalAlign "center"}
    (if editing
      (let [text-value (atom text)
            update-fn  (fn [ev val] (reset! text-value val))]
        [:> f/Stack.Item {:grow true}
         [:> f/Stack {:horizontal true}
          [:> f/Stack.Item {:grow true}
           [:> f/TextField {:value @text-value :onChange update-fn :onKeyDown update-fn}]]
          [:> f/DefaultButton {:onClick #(state/update-todo id @text-value)} "save"]]])
      ;; else
      [:<>
       [:> f/Checkbox {:label text :checked done :onChange #(state/toggle-done id)}]
       [:div
        [:> f/IconButton {:iconProps {:iconName "Edit"} :className "clearButton" :onClick #(state/edit-todo id)}]
        [:> f/IconButton {:iconProps {:iconName "Cancel"} :className "clearButton" :onClick #(state/delete-todo id)}]]])
    ]])

(defn todo-list []
  [:> f/Stack {:tokens {:childrenGap 10}}
   (map todo-item (state/filtered-todos))])

