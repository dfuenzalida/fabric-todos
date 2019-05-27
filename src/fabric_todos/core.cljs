(ns fabric-todos.core
  (:require [Fabric]
            [reagent.core :as r]))

;; Fabric elements ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def Button js/Fabric.PrimaryButton)
(def Rating js/Fabric.Rating)
(def Text js/Fabric.Text)

;; Views ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn home-page []
  [:div.ms-Grid
   [:div.ms-Grid-row
    [:> Text {:variant "large"} "Welcome to Reagent"]]
   ])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
