(ns gujtar.utils)

(defn filter-intervals
  [ns ivs filter-fn]
  (let [set-of-ns (into #{} ns)]
    (filter #(filter-fn
              set-of-ns
              (into #{} (ivs %)))
            (keys ivs))))
