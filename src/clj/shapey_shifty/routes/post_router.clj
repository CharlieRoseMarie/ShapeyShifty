(ns shapey-shifty.routes.post-router
  (:require [shapey-shifty.posts.core :as po]
            [shapey-shifty.posts.posts-io :as io]))

(defn get-post
  ([dt-path n] (io/read-post dt-path n))
  ([year month day n] (#(io/read-post % n) (io/create-path-by-date year month day))))
