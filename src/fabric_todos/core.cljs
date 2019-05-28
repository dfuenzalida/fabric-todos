(ns fabric-todos.core
  (:require [clojure.string :refer [blank?]]
            [Fabric]
            [reagent.core :as r]))

;; Fabric elements ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def Button js/Fabric.PrimaryButton)
(def Checkbox js/Fabric.Checkbox)
(def IconButton js/Fabric.IconButton)
(def Rating js/Fabric.Rating)
(def Pivot js/Fabric.Pivot)
(def PivotItem js/Fabric.PivotItem)
(def PrimaryButton js/Fabric.PrimaryButton)
(def Text js/Fabric.Text)
(def TextField js/Fabric.TextField)
(def Stack js/Fabric.Stack)

(defonce state (r/atom {:todos [] :counter 0 :labelInput ""}))
(def debug true) ;; using 'def' on purpose to throw state in between reloads

;; Header ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn pivot-filter [pivot-item]
  (println pivot-item.props.headerText))

(defn textfield-change [event newValue]
  (swap! state assoc :labelInput newValue)
  (when debug (println @state)))

(defn add-todo []
  (let [text (get @state :labelInput)
        id   (:counter (swap! state update :counter inc)) ]
    (when-not (blank? text)
      (swap! state assoc :labelInput "")
      (swap! state update :todos conj {:id id :text text :editing false :done false})
      (when debug (println @state)))))

(defn todo-header []
  [:> Stack
   [:> Stack {:horizontal true :horizontalAlign "center"}
    [:> Stack.Item {:align "center"}
     [:> Text {:variant "xxLarge"} "todos"]]]
   [:> Stack {:horizontal "horizontal"}
    [:> Stack.Item {:grow true}
     [:> TextField {:placeholder "What needs to be done?"
                    :value (:labelInput @state)
                    :onChange textfield-change}]]
    [:> PrimaryButton {:onClick add-todo} "Add"]]

   [:> Pivot {:onLinkClick pivot-filter}
    [:> PivotItem {:headerText "all"}]
    [:> PivotItem {:headerText "active"}]
    [:> PivotItem {:headerText "completed"}]]
   ])

;; Todo List ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn todo-item [{:keys [id text editing done] :as item}]
  [:> Stack {:key (str "stack" id) :horizontal true :verticalAlign "center" :horizontalAlign "space-between"}
   (when-not editing
     [:> Checkbox {:id id :key (str "check" id) :label text :checked done}
      [:div
       [:> IconButton {:iconProps {:iconName "Refresh"} :className "clearButton"}]
       [:> IconButton {:iconProps {:iconName "Refresh"} :className "clearButton"}]
       ]])])

(defn todo-list []
  [:> Stack {:gap 10}
   (map todo-item (:todos @state))
   ])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn todo-app []
  [:> Stack {:horizontalAlign "center"}
   [:> Stack {:style {:width 400} :gap "25"}
    (todo-header)
    (todo-list)
    ]
   ])

;; App initialization ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn mount-root []
  (r/render [todo-app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
