(ns gujtar.fingerings
  (require [gujtar.notes :as notes]
           [gujtar.neck :as neck]))

;; Temp
(def e-standard [:e :a :d :g :b :e])

(defn- nested-vectors
  [vecs]
  (map #(cond
          (nil? %) nil
          (sequential? %) %
          :else [%])
       vecs))

(defn- chord-permutations
  ([strings] (chord-permutations strings #{} []))
  ([strings set head]
   (if (empty? strings)
     (conj set head)
     (reduce #(chord-permutations (rest strings) % (conj head %2))
             set
             (first strings)))))

;; TODO: Take root note into account
(defn- sort-fingerings
  [fingerings]
  (sort-by #(apply max (filter identity %)) fingerings))

(defn notes
  "Converts a vector of fingerings to notes. Can take
  a regular vector: (notes [3 2 0 0 0 3])
  or a nested vector: (notes [[5 8] [5 7] [5 7] [5 7] [5 8] [5 8]]).
  Use nil to skip strings: (notes [nil nil 0 2 3 2])"
  ([strings] (notes strings e-standard))
  ([strings tuning]
   (let [strings (nested-vectors strings)]
     (->>
      (map vector tuning strings)
      (filter #(not (nil? (last %))))
      (map #(notes/absolute-intervals-to-notes (first %) (last %)))
      (map rest)
      (flatten)))))

(defn all
  "Finds all possible fingerings of a chord"
  ([notes] (all notes e-standard))
  ([notes tuning] (all notes tuning neck/default-reach))
  ([notes tuning reach]
   (let [ns        (into #{} notes)
         frets     (range 0 (inc neck/num-frets))
         neck      (neck/nilable (neck/filter-neck notes tuning))
         is-chord? #(= ns (into #{} (all % tuning)))]
     (->>
      (map #(neck/slice-neck neck % reach) frets)
      (map #(map last %))
      (map chord-permutations)
      (reduce into #{})
      (filter is-chord?)
      (sort-fingerings)))))

(defn scale-position
  ([ns fret] (scale-position ns fret e-standard))
  ([ns fret tuning]
   (let [min-fret #(>= % fret)]
     (->>
      (neck/slice-neck (neck/filter-neck ns tuning) fret)
      (map last)
      (map #(filter min-fret %))))))
