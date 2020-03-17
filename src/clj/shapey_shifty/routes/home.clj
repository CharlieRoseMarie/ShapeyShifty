(ns shapey-shifty.routes.home
  (:require
   [shapey-shifty.layout :as layout]
   [clojure.java.io :as io]
   [shapey-shifty.middleware :as mid]
   [ring.util.response]
   [shapey-shifty.posts.core :as posts]
   [shapey-shifty.posts.posts-io :as post-io]
   [shapey-shifty.routes.post-router :as post-router]
   [shapey-shifty.authors.author-core :as author]
   [ring.util.http-response :as response]))

(def middleware (atom [mid/wrap-csrf mid/wrap-formats]))

(defn home-page [request]
  (layout/render request "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn post-view [request]
  (let [{:keys [path-params query-params body-params]} request
        {:keys [year month day n]} path-params
        post (post-router/get-post year month day n)]
    (layout/render request "post.html" {:post (:properties post)
                                        :card (:author post)})))

(defn about-page [request]
  (layout/render request "h_card.html"
                 {:card (:card (author/load-author (get-in request [:path-params :name])))}))

(defn home-routes []
  [""
   {:middleware @middleware}
   ["/" {:get home-page}]
   ["/about/:name" {:get about-page}]
   ["/:year/:month/:day/:n" {:get post-view}]])

