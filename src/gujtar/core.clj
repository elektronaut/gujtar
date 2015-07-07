(ns gujtar.core
  (:require [gujtar.utils :as utils]
            [gujtar.notes :as notes]
            [gujtar.scales :as scales]
            [gujtar.chords :as chords]
            [gujtar.fretboard :as fretboard]
            [gujtar.fingerings :as fingerings]))

;;

(defn filter-root
  "Filters a set of scale or chord keys on root note"
  [pairs root]
  (filter #(= root (first %)) pairs))

(defn circle-of-fifths
  "Returns an infinite circle of fifths, default from :c."
  ([]  (circle-of-fifths :c))
  ([n] (iterate (notes/add-interval n 7))))


;; (defn -main
;;   "I don't do a whole lot ... yet."
;;   [& args]
;;   (println "Hello, World!"))
