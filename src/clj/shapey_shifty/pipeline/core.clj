(ns shapey-shifty.pipeline.core)

(def pipelines (atom {:load-post []
                      :render-post []
                      :write-post []}))

(defn update-pipeline [k f]
  (swap! pipelines #(update % k conj f)))

(defn add-load-post-step [f]
  (update-pipeline :load-post f))

(defn add-render-post-step [f]
  (update-pipeline :render-post f))

(defn add-write-post-step [f]
  (update-pipeline :write-post f))

(defn execute-pipeline [k params]
  (let [p (k @pipelines)]
    ((apply comp p) params)))

(defn add-pipeline [k v]
  (swap! pipelines #(assoc % k v)))
