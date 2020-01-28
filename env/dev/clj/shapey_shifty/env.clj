(ns shapey-shifty.env
  (:require
   [selmer.parser :as parser]
   [clojure.tools.logging :as log]
   [shapey-shifty.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[shapey-shifty started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[shapey-shifty has shut down successfully]=-"))
   :middleware wrap-dev})
