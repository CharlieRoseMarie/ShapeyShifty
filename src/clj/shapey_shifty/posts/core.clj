(ns shapey-shifty.posts.core)

(defn create-empty-post [] {:type nil :properties {:name nil :author nil :published nil :content nil}})

(defn set-publish-date [post date]
  (assoc-in post [:properties :published] date))

(defn set-type [post post-type]
  (assoc post :type post-type))

(defn set-author [post author]
  (assoc-in post [:properties :author] author)) 

(defn set-name [post post-name]
  (assoc-in post [:properties :name] post-name))

(defn set-content [post post-content]
 (assoc-in post [:properties :content] post-content)) 
