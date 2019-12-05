(ns day4.core
  (require [clojure.string :as str])
  (:gen-class))

(def prob-min 382345)
(def prob-max 843167)

(defn contains-repeating-digit [string] (not (empty? (re-seq #"([0-9])\1+" string))))

(defn numbers-increase [string]
  (let [str-vec (str/split string #"")]
    (loop [i 0]
      (cond
        (>
          (Integer/parseInt (get str-vec i))
          (Integer/parseInt (get str-vec (inc i))))
          false
        (= i (- (count str-vec) 2)) true
        :else (recur (inc i))))))

(defn test-val [acc val-to-test]
  (if (and
        (contains-repeating-digit (str val-to-test))
        (numbers-increase (str val-to-test)))
    (inc acc)
    acc))

(defn check []
  (println (reduce test-val 0 (range prob-min prob-max))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (check))
