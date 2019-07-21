(ns fabric-todos.fabric
  (:require ["office-ui-fabric-react" :as Fabric]))

;; Fabric elements ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def Checkbox Fabric/Checkbox)
(def DefaultButton Fabric/DefaultButton)
(def IconButton Fabric/IconButton)
(def Rating Fabric/Rating)
(def Pivot Fabric/Pivot)
(def PivotItem Fabric/PivotItem)
(def PrimaryButton Fabric/PrimaryButton)
(def Text Fabric/Text)
(def TextField Fabric/TextField)
(def Stack Fabric/Stack)

(defn init-icons!
  "Load the icon fonts. See https://github.com/OfficeDev/office-ui-fabric-react/wiki/Using-icons"
  []
  (Fabric/initializeIcons))
