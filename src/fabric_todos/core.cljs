(ns fabric-todos.core
  (:require [fabric-todos.fabric :as fab]
            [fabric-todos.header :as header]
            [fabric-todos.todo-list :as todo-list]
            [fabric-todos.footer :as footer]
            [reagent.core :as r]
            [reagent.dom :as rdom]))

;; Main application ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn todo-app []
  [:> fab/Stack {:horizontalAlign "center"}
   [:> fab/Stack {:style {:width 400} :gap 25}
    (header/todo-header)
    (todo-list/todo-list)
    (footer/todo-footer)]])

;; App initialization ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn mount-root []
  (rdom/render [todo-app] (.getElementById js/document "app")))

(defn init! []
  (mount-root)
  (header/focus-new-todo))
