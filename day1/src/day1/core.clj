(ns day1.core
  (:require [clojure.java.io :as io])
  (:gen-class))

;; define input file name
(def input-one "input1.txt")

;; line in text file to integer
(defn parse-int
  [line]
  (Integer. (re-find #"\d+" line)))

;; calculate fuel (in unspecified units) needed for given mass
(defn calc-fuel [mass]
  (- (int (Math/floor (/ mass 3))) 2))

;; add fuel from mass as string to current total value
(defn mass-to-fuel [total mass]
  (+ total (calc-fuel (parse-int mass))))

;; iterate over fuel value to calculate extra
(defn calc-self-fuel [fuel]
  (loop [total fuel
        current fuel]
    (let [additional-fuel (calc-fuel current)]
      (if (> additional-fuel 0)
        (recur (+ total additional-fuel) additional-fuel)
        total))))

;; add fuel from mass string PLUS FUEL NEEDED TO LIFT FUEL to total
(defn mass-to-fuel-including-fuel [total mass]
  (+ total (calc-self-fuel (calc-fuel (parse-int mass)))))

;; read file and reduce resultant sequence
(defn file-to-red-seq
  [file f]
  (with-open [rdr (io/reader (io/resource file))]
    (reduce f 0 (line-seq rdr))))

;; call file read function with plain fuel calculation function
(defn get-fuel [file]
  (file-to-red-seq file mass-to-fuel))

;; call file read function with self-additional fuel calculation function
(defn get-fuel-for-fuel [file]
  (file-to-red-seq file mass-to-fuel-including-fuel))

;; call funs to calc part 1 and 2 values
(defn -main [& args]
  (println (get-fuel input-one))
  (println (get-fuel-for-fuel input-one)))
