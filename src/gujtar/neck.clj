(ns gujtar.neck
  (:require [gujtar.notes :as notes]))

;; Temp
(def e-standard [:e :a :d :g :b :e])

(def num-frets 24)
(def default-reach 4)

(defn complete
  "Returns a neck with 24 frets, defaults to standard tuning"
  ([] (complete e-standard))
  ([tuning]
   (let [frets (range 0 (inc num-frets))]
     (map #(vector % frets) tuning))))

(defn nilable
  "Adds nil to possible positions on a neck"
  [neck]
  (map #(vector (first %) (conj (last %) nil)) neck))

(defn- filter-string
  "Filters a single string, returning only the frets that match the given notes"
  [string ns]
  (let [[root frets] string
        set-of-ns (into #{} ns)]
    [root
     (filter #(contains? set-of-ns (notes/add-interval root %)) frets)]))

(defn filter-neck
  "Returns a neck containing only the given notes. Defaults to standard tuning."
  ([ns] (filter-neck ns e-standard))
  ([ns tuning]
   (->>
    (complete tuning)
    (map #(filter-string % ns)))))

(defn slice-neck
  "Returns a cross-section of the neck starting from a fret.
  The open string is included, since it's accessible from every position"
  ([neck fret] (slice-neck neck fret default-reach))
  ([neck fret reach]
   (let [reachable (into #{nil 0} (range fret (+ fret reach)))
         reachable? #(contains? reachable %)]
     (map #(vector
            (first %)
            (filter reachable? (last %)))
          neck))))
