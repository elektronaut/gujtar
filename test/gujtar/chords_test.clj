(ns gujtar.chords-test
  (:require [clojure.test :refer :all]
            [gujtar.chords :refer :all]))

(defn in? [seq el] (some #(= el %) seq))
(defn not-in? [seq el] (not (in? seq el)))

(deftest test-get-chord
  (testing "Finding chords"
    (is (= [:a :c :d# :f#] (get-chord :a :dim7)))))

(deftest test-invert
  (testing "Inverting chords"
    (is (= [:e :a :c] (invert [:a :c :e])))))

(deftest test-in-key
  (testing "Finding chords in key"
    (is (= #{:a} (into #{} (map first (map first (in-key :a))))))
    (is (in? (keys (in-key :a)) [:a :min6-add9]))
    (is (= [:a :c :e :g :b :f#] ((in-key :a) [:a :min13])))))

(deftest test-search-partial
  (testing "Searching partial chords"
    (is (= #{:a}
           (into #{} (map first (search-partial [:a :c :e] (in-key :a))))))
    (is (in? (search-partial [:a :c :e]) [:d :eleventh]))
    (is (in? (search-partial [:a :c :e]) [:a :min7]))
    (is (not-in? (search-partial [:a :c :e]) [:a :maj]))))

(deftest test-search
  (testing "Searching chords"
    (is (= #{:a}
           (into #{} (map first (search [:a :c :e] (in-key :a))))))
    (is (in? (search [:a :c :e]) [:a :min]))
    (is (not-in? (search [:a :c :e]) [:a :min7]))))

(deftest test-search-in
  (let [test-scale [:c :d :e :f :g :a :b]]
   (testing "Searching chords in scale"
    (is (= #{:a}
           (into #{} (map first (search-in test-scale (in-key :a))))))
    (is (in? (search-in test-scale) [:g :maj]))
    (is (not-in? (search-in test-scale) [:g :min])))))
