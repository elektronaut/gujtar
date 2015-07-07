(ns gujtar.notes-test
  (:require [clojure.test :refer :all]
            [gujtar.notes :refer :all]))

(deftest test-all
  (testing "All notes"
    (is (= [:c :c# :d :d# :e :f :f# :g :g# :a :a# :b] all))))

(deftest test-chromatic-scale
  (testing "The chromatic scale"
    (is (= [:c :c# :d :d# :e :f :f# :g :g# :a :a# :b] (chromatic-scale)))
    (is (= [:g :g# :a :a# :b :c :c# :d :d# :e :f :f#] (chromatic-scale :g)))))

(deftest test-add-interval
  (testing "Adding intervals"
    (is (= :d# (add-interval :c 3)))
    (is (= :e  (add-interval :a 7)))))

(deftest test-intervals-to-notes
  (testing "Converting intervals to notes"
    (is (= [:a :b :c :d :e :f :g]  (intervals-to-notes :a [2 1 2 2 1 2])))))

(deftest test-absolute-intervals-to-notes
  (testing "Converting intervals to notes"
    (is (= [:a :b :c :d :e :f :g]  (absolute-intervals-to-notes :a [2 3 5 7 8 10])))))

(deftest test-interval
  (testing "Calculating interval"
    (is (= 7  (interval :a :e)))))

(deftest test-notes-to-intervals
  (testing "Converting notes to intervals"
    (is (= [2 1 2 2 1 2]  (notes-to-intervals [:a :b :c :d :e :f :g])))))
