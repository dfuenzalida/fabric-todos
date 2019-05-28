(ns fabric-todos.core
  (:require [clojure.string :refer [blank?]]
            [Fabric]
            [reagent.core :as r]))

;; Fabric elements ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def Button js/Fabric.PrimaryButton)
(def Checkbox js/Fabric.Checkbox)
(def DefaultButton js/Fabric.DefaultButton)
(def IconButton js/Fabric.IconButton)
(def Rating js/Fabric.Rating)
(def Pivot js/Fabric.Pivot)
(def PivotItem js/Fabric.PivotItem)
(def PrimaryButton js/Fabric.PrimaryButton)
(def Text js/Fabric.Text)
(def TextField js/Fabric.TextField)
(def Stack js/Fabric.Stack)

(defonce state (r/atom {:todos [] :counter 0 :labelInput "" :filter "all"}))
(def debug true) ;; using 'def' on purpose to throw state in between reloads

;; State APIs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-todo [text]
  (let [id (:counter (swap! state update :counter inc))]
    (swap! state update :todos conj {:id id :text text :editing false :done false})))

(defn delete-todo [id]
  (let [new-todos (into [] (remove #(= id (:id %)) (:todos @state)))]
    (swap! state assoc :todos new-todos)))

(defn edit-todo [id]
  (let [todos (:todos @state)]
    (swap! state assoc :todos
           (mapv #(if (= id (:id %))
                    (update-in % [:editing] false?)
                    %) todos))))

(defn update-todo [id]
  ;; TODO implement
  (edit-todo id))

(defn toggle-done [id]
  (println id)
  (let [todos (:todos @state)]
    (swap! state assoc :todos
           (mapv #(if (= id (:id %))
                    (update-in % [:done] false?)
                    %) todos))
    ;; (swap! state update-in [:todos id :done] false?)
    (when debug (println @state))))

(defn update-filter [label]
  (swap! state assoc :filter label))

;; Header ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn pivot-filter [pivot-item]
  (update-filter pivot-item.props.headerText))

(defn textfield-change [event newValue]
  (swap! state assoc :labelInput newValue)
  (when debug (println @state)))

(defn add-btn-handler []
  (let [text (get @state :labelInput)]
    (when-not (blank? text)
      (add-todo text)
      (swap! state assoc :labelInput "")
      (.focus (.getElementById js/document "newTodo"))
      (when debug (println @state)))))

(defn todo-header []
  [:> Stack
   [:> Stack {:horizontal true :horizontalAlign "center"}
    [:> Stack.Item {:align "center"}
     [:> Text {:variant "xxLarge"} "todos"]]]
   [:> Stack {:horizontal "horizontal"}
    [:> Stack.Item {:grow true}
     [:> TextField {:id "newTodo"
                    :placeholder "What needs to be done?"
                    :value (:labelInput @state)
                    :onKeyDown #(when (= 13 (.-which %)) (add-btn-handler))
                    :onChange textfield-change}]]
    [:> PrimaryButton {:onClick add-btn-handler} "Add"]]

   [:> Pivot {:onLinkClick pivot-filter}
    [:> PivotItem {:headerText "all"}]
    [:> PivotItem {:headerText "active"}]
    [:> PivotItem {:headerText "completed"}]]
   ])

;; Todo List ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn todo-item [{:keys [id text editing done] :as item}]
  [:div {:key (str "stack" id)}
   [:> Stack {:horizontal true :horizontalAlign "space-between" :verticalAlign "center" }
    (if editing
      [:> Stack.Item {:grow true}
       [:> Stack {:horizontal "horizontal"}
        [:> Stack.Item {:grow true}
         [:> TextField {:value text}]]
        [:> DefaultButton {:onClick #(update-todo id)} "save"]]]
      ;; else
      [:> Stack.Item {:grow true}
       [:> Stack {:horizontal true}
        [:> Stack.Item {:grow true}
         [:> Checkbox {:label text :checked done :onChange #(toggle-done id)}]]
        [:> Stack
         [:> IconButton {:iconProps {:iconName "Edit"} :className "clearButton" :onClick #(edit-todo id)}]]
        [:> IconButton {:iconProps {:iconName "Cancel"} :className "clearButton" :onClick #(delete-todo id)}]]
       ]
      )]])

(defn todo-list []
  (let [remove-map {"all" nil "active" true "completed" false}
        remove-on  (get remove-map (:filter @state))
        filtered-todos (remove #(= remove-on (:done %)) (:todos @state))]
    [:> Stack {:gap 10}
     (map todo-item filtered-todos)
     ]))

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
