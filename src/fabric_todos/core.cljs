(ns fabric-todos.core
  (:require [Fabric]
            [reagent.core :as r]))

;; Fabric elements ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def Button js/Fabric.PrimaryButton)
(def Rating js/Fabric.Rating)
(def Pivot js/Fabric.Pivot)
(def PivotItem js/Fabric.PivotItem)
(def PrimaryButton js/Fabric.PrimaryButton)
(def Text js/Fabric.Text)
(def TextField js/Fabric.TextField)
(def Stack js/Fabric.Stack)

(defonce state (r/atom {:todos [] :counter 0 :labelInput ""}))
(def debug true) ;; using 'def' on purpose to throw state in between reloads
;; Components ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn pivot-filter [pivot-item]
  (println pivot-item.props.headerText))

(defn textfield-change [event newValue]
  (swap! state assoc :labelInput newValue)
  (when debug (println @state)))

(defn add-todo []
  (let [text (get @state :labelInput)
        id   (:counter (swap! state update :counter inc)) ]
    (swap! state assoc :labelInput "")
    (swap! state update :todos conj {:id id :text text :done false})
    (when debug (println @state))))

(defn todo-header []
  [:> Stack
   [:> Stack {:horizontal true :horizontalAlign "center"}
    [:> Stack.Item {:align "center"}
     [:> Text {:variant "xxLarge"} "todos"]]]
   [:> Stack {:horizontal "horizontal"}
    [:> Stack.Item {:grow true}
     [:> TextField {:placeholder "What needs to be done?"
                    :value (get-in @state [:labelInput])
                    :onChange textfield-change}]]
    [:> PrimaryButton {:onClick add-todo} "Add"]]

   [:> Pivot {:onLinkClick pivot-filter}
    [:> PivotItem {:headerText "all"}]
    [:> PivotItem {:headerText "active"}]
    [:> PivotItem {:headerText "completed"}]]
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
