(ns gujtar.scales
  (:require clojure.set
            [gujtar.utils :as utils]
            [gujtar.notes :as notes]))

(def intervals
  {:chromatic            [1 1 1 1 1 1 1 1 1 1 1]
   :major                [2 2 1 2 2 2]
   :natural-minor        [2 1 2 2 1 2]
   :harmonic-minor       [2 1 2 2 1 3]
   :melodic-minor-asc    [2 1 2 2 2 2]
   :melodic-minor-desc   [2 2 1 2 2 1]
   :minor-pentatonic     [3 2 2 3]
   :major-pentatonic     [2 2 3 2]
   :harmonic-major       [2 2 1 2 1 3]
   :dorian               [2 1 2 2 2 1]
   :mixolydian           [2 2 1 2 2 1]
   :lydian               [2 2 2 1 2 2]
   :lydian-augmented     [2 2 2 2 1 2]
   :aeolian-dominant     [2 2 1 2 1 2]
   :phrygian             [1 2 2 2 1 2]
   :phrygian-dominant    [1 3 1 2 1 2]
   :half-diminished      [2 1 2 1 2 2]
   :altered              [1 2 1 2 2 2]
   :acoustic             [2 2 2 1 2 1]
   :bebop-dominant       [2 2 1 2 2 1 1]
   :whole-tone           [2 2 2 2 2]
   :augmented            [3 1 3 1 3]
   :prometheus           [2 2 2 3 1]
   :blues                [3 2 1 1 3]
   :tritone              [1 3 2 1 3]
   :two-semitone-tritone [1 1 4 1 1]
   :persian              [1 3 1 1 2 3]
   :double-harmonic      [1 3 1 2 1 3]
   :hungarian-minor      [2 1 3 1 1 3]
   :hungarian-gypsy      [2 1 3 1 1 2]
   :ukrainian-dorian     [2 1 3 1 2 1]
   :neapolitan-minor     [1 2 2 2 1 3]
   :neapolitan-major     [1 2 2 2 2 2]
   :algerian             [2 1 3 1 1 3 1 2 1 2 2 1 3]
   :enigmatic            [1 3 2 2 2 1]
   :ahava-raba           [1 3 1 2 1 2]})

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
  "Finds all scales that matches a set of notes"
  ([ns]        (search ns all-scales))
  ([ns scales] (utils/filter-intervals ns scales #(= % %2))))

(defn search-partial
  "Finds all scales that contains a set of notes"
  ([ns]        (search-partial ns all-scales))
  ([ns scales] (utils/filter-intervals ns scales #(clojure.set/subset? % %2))))
