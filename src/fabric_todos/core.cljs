(ns fabric-todos.core
  (:require [Fabric]
            [reagent.core :as r]))

;; Fabric elements ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def Button js/Fabric.PrimaryButton)
(def Rating js/Fabric.Rating)
(def PrimaryButton js/Fabric.PrimaryButton)
(def Text js/Fabric.Text)
(def TextField js/Fabric.TextField)
(def Stack js/Fabric.Stack)

;; Components ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn todo-header []
  [:> Stack
   [:> Stack {:horizontal true :horizontalAlign "center"}
    [:> Stack.Item {:align "center"}
     [:> Text {:variant "xxLarge"} "todos"]]]
   [:> Stack {:horizontal "horizontal"}
    [:> Stack.Item {:grow true}
     [:> TextField {:placeholder "What needs to be done?"}]]
    [:> PrimaryButton "Add"]]
   ])

(defn todo-app []
  [:> Stack {:horizontalAlign "center"}
   [:> Stack {:style {:width 400} :gap "25"}
    (todo-header)
    ]]
  )

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [todo-app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
