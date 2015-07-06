(ns gujtar.notes)

(def all [:c :c# :d :d# :e :f :f# :g :g# :a :a# :b])

(defn- rotate
  [seq]
  (concat (rest seq) [(first seq)]))

(defn- start-from
  [n s]
  (if (= n (first s))
    s
    (start-from n (rotate s))))

(defn chromatic-scale
  "Returns the chromatic scale, default from :c"
  ([]  (chromatic-scale :c))
  ([n] (start-from n all)))

(defn add-interval
  "Shifts note n by interval i"
  [n i]
  (nth (cycle (chromatic-scale n)) i))

(defn intervals-to-notes
  "Reduces a sequence of intervals relative to each other to a series of
  notes from the root n"
  [n is]
  (reduce #(conj % (add-interval (last %) %2)) [n] is))

(defn absolute-intervals-to-notes
  "Reduces a sequence of intervals from the root to a series of notes"
  [n is]
  (conj (map #(add-interval n %) is) n))

(defn interval
  "Determine interval between two notes"
  [n n2]
  (.indexOf (chromatic-scale n) n2))

(defn notes-to-intervals
  "Converts a sequence of notes to a sequence of intervals"
  [ns]
  (let [head (first ns)
        tail (rest ns)]
    (->> (reduce #(conj % [%2 (interval (first (last %)) %2)]) [[head 0]] tail)
         (map last)
         (drop 1))))
