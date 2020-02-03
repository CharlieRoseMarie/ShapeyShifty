(ns shapey-shifty.posts.posts-io
  (:require
   [shapey-shifty.posts.core :as core]
   [shapey-shifty.authors.author-core :as author]))

(def post-filename "post.edn")
(def base-posts-path "resources/posts")

(defn create-path-by-date [year month day]
  {:year year :month month :day day})

(defn pathmap-to-path [{:keys [year month day]}]
  (format "%s/%s/%s" year month day))

(defn count-posts-in-date [dt-path]
  (let [path (pathmap-to-path dt-path)
        final-path (format "%s/%s" base-posts-path path)]
    (->> final-path
         clojure.java.io/file
         file-seq
         (filter #(.isDirectory %))
         count
         dec)))

(defn write-post [post dt-path]
  (let [path (pathmap-to-path dt-path)
        increment (inc (count-posts-in-date dt-path))
        final-path (format "%d/%d/%d/%d" base-posts-path path increment post-filename)]
    (clojure.java.io/make-parents final-path)
    (spit final-path (pr-str post))))

(defn assoc-author [post]
  (let [filename (get-in post [:properties :author])
        author (author/load-author filename)
        card (get author :card)]
    (assoc post :author card)))

(defn read-post
  ([file]
   (when (.exists file)
     (-> file slurp read-string assoc-author)))
  ([dt-path n]
   (let [path (format "%s/%s/%s/%s" base-posts-path (pathmap-to-path dt-path) n post-filename)
         f (clojure.java.io/file path)]
     (read-post f))))
