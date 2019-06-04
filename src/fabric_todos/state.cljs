(ns fabric-todos.state
  (:require [reagent.core :as r]))

;; Global app state ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defonce state (r/atom {:todos [] :counter 0 :labelInput "" :filter "all"}))
(def debug false) ;; using 'def' on purpose to throw state in between reloads

;; State APIs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-todo [text]
  (let [id (:counter (swap! state update :counter inc))]
    (swap! state update :todos conj {:id id :text text :editing false :done false})))

(defn delete-todo [id]
  (let [new-todos (into [] (remove #(= id (:id %)) (:todos @state)))]
    (swap! state assoc :todos new-todos)))

(defn toggle-attribute [id attr]
  (let [todos (:todos @state)]
    (swap! state assoc :todos
           (mapv
            #(if (= id (:id %)) (update-in % [attr] false?) %)
            todos))))

(defn edit-todo [id]
  (toggle-attribute id :editing))

(defn update-todo [id]
  ;; TODO implement
  (edit-todo id))

(defn toggle-done [id]
  (toggle-attribute id :done))

(defn update-filter [label]
  (swap! state assoc :filter label))

(defn clear-completed []
  (let [items (into [] (remove #(= true (:done %)) (:todos @state)))]
    (swap! state assoc :todos items)))

(defn update-label-input [new-value]
  (swap! state assoc :labelInput new-value)
  (when debug (println state)))

(defn new-todo-value []
  (:labelInput @state))

(defn remaining-todos []
  (remove #(= true (:done %)) (:todos @state)))

(defn filtered-todos []
  (let [remove-map {"all" nil "active" true "completed" false}
        remove-on  (get remove-map (:filter @state))]
    (remove #(= remove-on (:done %)) (:todos @state))))

