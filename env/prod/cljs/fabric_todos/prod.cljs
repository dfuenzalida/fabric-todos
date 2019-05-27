(ns fabric-todos.prod
  (:require
    [fabric-todos.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
