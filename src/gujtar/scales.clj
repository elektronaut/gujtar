(ns gujtar.scales
  (:require clojure.set
            [gujtar.utils :as utils]
            [gujtar.notes :as notes]))

(def intervals
  {:major              [2 2 1 2 2 2]
   :natural-minor      [2 1 2 2 1 2]
   :harmonic-minor     [2 1 2 2 1 3]
   :melodic-minor-asc  [2 1 2 2 2 2]
   :melodic-minor-desc [2 2 1 2 2 1]
   :minor-pentatonic   [3 2 2 3]
   :dorian             [2 1 2 2 2 1]
   :mixolydian         [2 2 1 2 2 1]
   :ahava-raba         [1 3 1 2 1 2]})

(defn get-scale
  "Returns a set of notes from a root note and scale name"
  [root scale-name]
  (notes/intervals-to-notes root (intervals scale-name)))

(defn in-key
  "Returns all scales in a given key"
  [key]
  (reduce #(assoc % [key %2] (get-scale key %2))
          {}
          (keys intervals)))

(def all-scales
  "A map of all scales in all keys"
  (reduce #(merge % (in-key %2)) {} notes/all))

(defn search
  "Finds all scales that contains a set of notes"
  ([ns]        (search ns all-scales))
  ([ns scales] (utils/filter-intervals ns scales #(clojure.set/subset? % %2))))
