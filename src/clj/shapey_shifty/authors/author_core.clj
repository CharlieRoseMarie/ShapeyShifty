(ns shapey-shifty.authors.author-core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def base-path "resources/author")

(defn create-author [] 
  {:card nil :password-hash nil})

(defn load-author [author-name] 
  (let [path (format "%s/%s" base-path author-name)
        file (io/file path)]
    (when (.exists file)
      (->> file
           slurp
           edn/read-string))))

(defn load-all-authors []
    (->> base-path 
        io/file
        file-seq
        (filter #(.isFile %))
        (map #(->> % slurp edn/read-string))))

