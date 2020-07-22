(ns shapey-shifty.index.index
  (:require [clucy.core :as clucy]
            [duratom.core :as dur]
            [shapey-shifty.posts.posts-io :as post-io]))

(def index-path (atom "resources/index"))

(defn create-index [index-path]
  (let [index (dur/duratom :local-file :file-path index-path :init [])]
    index))

(def post-index (clucy/disk-index @index-path))

(defn add-post-to-index [index post]
  (let [metadata (dissoc post :content)]
    (if-let [existing-post (empty (filter #(= (:key %) (:key post)) index))]
      (conj index metadata))))

(defn crawl-posts!
  ([path]
   (crawl-posts! path post-io/read-post))
  ([path parsing-fn]
   (->> path
        clojure.java.io/file
        file-seq
        (filter #(.isFile %))
        (mapv parsing-fn)
        (apply add-post-to-index))))
