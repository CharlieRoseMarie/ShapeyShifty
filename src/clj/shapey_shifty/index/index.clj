(ns shapey-shifty.index.index
  (:require [clucy.core :as clucy]
            [shapey-shifty.posts.posts-io :as post-io]
            ))

(def index-path (atom "resources/index"))

(def post-index (clucy/disk-index @index-path))

(defn add-post-to-index [post]
  (clucy/add post-index post))

(defn crawl-posts!
  ([path]
   (crawl-posts! path post-io/read-post))
  ([path parsing-fn] 
   (->> path
        clojure.java.io/file
        file-seq
        (filter #(.isFile %))
        (mapv #(parsing-fn %))
        (apply add-post-to-index))))
