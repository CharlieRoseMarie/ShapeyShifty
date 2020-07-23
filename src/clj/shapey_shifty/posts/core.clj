(ns shapey-shifty.posts.core
  (:require [clojure.spec.alpha :as s]))

(defn create-empty-post [] {::type :note ::key (java.util.UUID/randomUUID) ::content ""
                            ::properties
                            {::name nil
                             ::author nil
                             ::published nil
                             ::created (java.time.LocalDateTime/now)
                             ::stub nil
                             ::filename nil
                             ::status :preview}})

(s/def ::name (s/nilable string?))
(s/def ::author (s/nilable string?))
(s/def ::published (s/nilable keyword?))
(s/def ::content (s/nilable string?))
(s/def ::stub (s/nilable  string?))
(s/def ::filename (s/nilable string?))
(s/def ::type keyword?)
(s/def ::key uuid?)
(s/def ::status keyword?)

(s/def ::properties
  (s/keys :req [::name ::author ::published ::stub ::filename ::created]))

(s/def ::post
  (s/keys :req [::type ::properties ::content]))

(defprotocol PostKeeper
  (create-post [this post])
  (search-posts [this post-filter index])
  (get-all-posts [this])
  (update-post [this post])
  (delete-post [this post]))

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
