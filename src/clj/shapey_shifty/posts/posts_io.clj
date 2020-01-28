(ns shapey-shifty.posts.posts-io
  (:require 
    [clojure.data.json :as json]
    [shapey-shifty.posts.core :as core]))

(def post-filename "post.json")
(def base-path "resources/posts")


(defn create-path-by-date [year month day] 
  {:year year :month month :day day})

(defn pathmap-to-path [{:keys [year month day]}]
  (format "%d/%d/%d" year month day))

(defn count-posts-in-date [dt-path]
  (let [path (pathmap-to-path dt-path)
        final-path (format "%s/%s" base-path path)]
    (->> final-path
         clojure.java.io/file
         file-seq
         (filter #(.isDirectory %))
         count
         dec)))

(defn jsonify-post [post]
  (json/write-str post))

(defn write-post [post dt-path]
  (let [path (pathmap-to-path dt-path)
        increment (inc (count-posts-in-date dt-path))
        final-path (format "%s/%s/%d/%s" base-path path increment post-filename)] 
    (clojure.java.io/make-parents final-path)
    (spit final-path post)))

