(ns fabric-todos.header
  (:require [clojure.string :refer [blank?]]
            ["@fluentui/react" :as f]
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
  [:> f/Stack
   [:> f/Stack {:horizontal true :horizontalAlign "center"}
    [:> f/Stack.Item {:align "center"}
     [:> f/Text {:variant "xxLarge"} "todos"]]]
   [:> f/Stack {:horizontal "horizontal"}
    [:> f/Stack.Item {:grow true}
     [:> f/TextField {:id "newTodo"
                        :placeholder "What needs to be done?"
                        :value (state/new-todo-value)
                        :onKeyDown #(when (= 13 (.-which %)) (add-btn-handler))
                        :onChange textfield-change}]]
    [:> f/PrimaryButton {:onClick add-btn-handler} "Add"]]
   [:> f/Pivot {:onLinkClick pivot-filter}
    [:> f/PivotItem {:headerText "all"}]
    [:> f/PivotItem {:headerText "active"}]
    [:> f/PivotItem {:headerText "completed"}]]])
