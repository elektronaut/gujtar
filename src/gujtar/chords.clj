(ns gujtar.chords
  (:require clojure.set
            [gujtar.utils :as utils]
            [gujtar.notes :as notes]))

(def intervals
  {:maj             [4 3]
   :third           [4]
   :fifth           [7]
   :sixth           [4 3 2]
   :sixth-add-9     [4 3 2 5]
   :dom7            [4 3 3]
   :dom7-#9         [4 3 3 5]
   :dom7-#11        [4 3 3 8]
   :dom7-b5         [4 2 4]
   :dom7-b9         [4 3 3 3]
   :dom7-b5-#9      [4 2 4 5]
   :dom7-sus4       [5 2 3]
   :ninth           [4 3 3 4 4]
   :ninth-b5        [4 2 4 4]
   :ninth-#11       [4 3 3 4]
   :ninth-sus4      [5 2 3 4]
   :eleventh        [4 3 3 4 3]
   :thirteenth      [4 3 3 4 7]
   :thirteenth-b9   [4 3 3 3 8]
   :thirteenth-sus4 [5 2 3 4 7]
   :add9            [4 3 7]
   :dim             [3 3]
   :dim7            [3 3 3]
   :min             [3 4]
   :min3            [3]
   :min6            [3 4 2]
   :min-b6          [3 4 1]
   :min6-add9       [3 4 2 5]
   :min7            [3 4 3]
   :min7-b5         [3 3 4]
   :min9            [3 4 3 4]
   :min9-b5         [3 3 4 4]
   :min9-maj7       [3 4 4 3]
   :min11           [3 4 3 4 3]
   :min13           [3 4 3 4 7]
   :min-add9        [3 4 7]
   :min-maj7        [3 4 4]
   :maj7            [4 3 4]
   :maj7-b5         [4 2 5]
   :maj7-#11        [4 3 4 7]
   :maj9            [4 3 4 3]
   :maj13           [4 3 4 3 7]
   :sus2            [2 5]
   :sus4            [5 2]
   :aug             [4 4]
   :dom7-#5         [4 4 2]
   :dom7-#5-#9      [4 4 2 5]
   :dom7-#5-b9      [4 4 2 3]
   :ninth-#5        [4 4 2 4]})

(defn get-chord
  "Returns a set of notes from a root note and chord name"
  [root chord-name]
  (notes/intervals-to-notes root (intervals chord-name)))

(defn invert
  "Inverts a chord by one degree"
  [notes]
  (concat [(last notes)] (drop-last notes)))

(defn in-key
  "Returns all chords in a given key"
  [key]
  (reduce #(assoc % [key %2] (get-chord key %2))
          {}
          (keys intervals)))

(def all-chords
  "A map of all chords in all keys"
  (reduce #(merge % (in-key %2)) {} notes/all))

(defn search
  "Finds all chords that matches a set of notes"
  ([notes] (search notes all-chords))
  ([notes chord-list] (utils/filter-intervals notes chord-list #(= % %2))))

(defn search-partial
  "Finds all chords that contains a set of notes"
  ([notes] (search-partial notes all-chords))
  ([notes chord-list]
   (utils/filter-intervals notes chord-list #(clojure.set/subset? % %2))))

(defn search-in
  "Finds all chords that is a subset of a set of notes"
  ([notes] (search-in notes all-chords))
  ([notes chord-list]
   (utils/filter-intervals notes chord-list #(clojure.set/subset? %2 %))))
