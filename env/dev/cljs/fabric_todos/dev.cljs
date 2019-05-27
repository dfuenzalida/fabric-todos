(ns ^:figwheel-no-load fabric-todos.dev
  (:require
    [fabric-todos.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
