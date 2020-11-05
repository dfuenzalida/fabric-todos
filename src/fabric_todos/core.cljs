(ns fabric-todos.core
  (:require ["@fluentui/react" :as f]
            ["@uifabric/icons" :as ui]
            [reagent.dom :as rdom]
            [fabric-todos.header :as header]
            [fabric-todos.todo-list :as todo-list]
            [fabric-todos.footer :as footer]))

;; Main application ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn todo-app []
  [:> f/Stack {:horizontalAlign "center"}
   [:> f/Stack {:style {:width 400} :tokens {:childrenGap 25}}
    (header/todo-header)
    (todo-list/todo-list)
    (footer/todo-footer)]])

;; App initialization ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn mount-root []
  (rdom/render [todo-app] (.getElementById js/document "app")))

(defn init-ui []
  (mount-root))

(defn main! []
  (println "starting...")
  (ui/initializeIcons)
  (init-ui))

(defn ^:dev/after-load reload! []
  (println "reloading...")
  (init-ui))
