(ns fabric-todos.state
  (:require [reagent.core :as r]))

;; Global app state ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defonce state (r/atom {:todos (sorted-map) :counter 0 :labelInput "" :filter "all"}))
(def debug false) ;; using 'def' on purpose to throw state in between reloads

;; State APIs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-todo [text]
  (let [id (:counter (swap! state update :counter inc))]
    (swap! state update :todos conj {id {:id id :text text :editing false :done false}})))

(defn delete-todo [id]
  (swap! state update-in [:todos] dissoc id))

(defn toggle-attribute [id attr]
  (swap! state update-in [:todos id attr] false?))

(defn edit-todo [id]
  (toggle-attribute id :editing))

(defn update-todo [id text]
  (swap! state update-in [:todos id] merge {:editing false :text text}))

(defn toggle-done [id]
  (toggle-attribute id :done))

(defn update-filter [label]
  (swap! state assoc :filter label))

(defn clear-completed []
  (let [completed-ids (map first (filter #(get-in % [1 :done]) (:todos @state)))
        active-todos  (reduce dissoc (:todos @state) completed-ids)]
    (swap! state assoc :todos active-todos)))

(defn update-label-input [new-value]
  (swap! state assoc :labelInput new-value)
  (when debug (println state)))

(defn new-todo-value []
  (:labelInput @state))

(defn remaining-todos []
  (map second
       (remove #(= true (get-in % [1 :done])) (:todos @state))))

(defn filtered-todos []
  (let [remove-map {"all" nil "active" true "completed" false}
        remove-on  (get remove-map (:filter @state))]
    (remove #(= remove-on (:done %)) (map second (:todos @state)))))

