(ns shapey-shifty.index.index
  (:require
   [clojure.spec.alpha :as s]
   [duratom.core :as dur]
   [shapey-shifty.posts.posts-io :as post-io]))

(s/def ::index-item (s/keys :req [::filename ::key ::created-date ::stub]))
(s/def ::index (s/coll-of ::index-item))

(defn create-index [index-path]
  (let [index (dur/duratom :local-file :file-path index-path :init [])]
    index))

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
