(ns shapey-shifty.posts.core
  (:require [clojure.spec.alpha :as s]))

(def posts (atom []))

(defn create-empty-post [] {::type :note ::key (java.util.UUID/randomUUID) ::content ""
                            ::properties
                            {::name nil
                             ::author nil
                             ::published nil
                             ::stub nil
                             ::filename nil
                             ::status :preview}})

(s/def ::name (s/nilable string?))
(s/def ::author string?)
(s/def ::published keyword?)
(s/def ::content string?)
(s/def ::stub string?)
(s/def ::filename (s/nilable string?))
(s/def ::type keyword?)
(s/def ::key uuid?)
(s/def ::status keyword?)

(s/def ::properties
  (s/keys :req [::name ::author ::published ::stub ::filename]))

(s/def ::post
  (s/keys :req [::type ::properties ::content]))

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
