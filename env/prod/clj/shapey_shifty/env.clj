(ns shapey-shifty.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[shapey-shifty started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[shapey-shifty has shut down successfully]=-"))
   :middleware identity})
