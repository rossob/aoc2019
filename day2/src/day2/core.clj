(ns day2.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(def filename "day2pt1input.txt")
(def goal-output 19690720)

;; read intcode string to vector of ints
(defn get-cpu []
  (vec (map read-string (str/split (slurp (io/resource filename)) #","))))

;; stated in problem for setup -
;; replace position 1 with the value 12 and replace position 2 with the value 2
(defn set-alarm-state [cpu x y]
  (assoc (assoc cpu 2 y) 1 x))

;; calculate program alarm from given inputs (100 * x + y)
(defn calc-program-alarm [x y]
  (+ (* 100 x) y))

;; set value at n+3 using values at n+1 and n+2
(defn process-cpu [cpu pos process-fn]
  (assoc cpu (nth cpu (+ pos 3))
    (process-fn (nth cpu (nth cpu (+ pos 1))) (nth cpu (nth cpu (+ pos 2))))))

;; process cpu until opcode 99 or invalid opcode reached
(defn process-opcode [cpu]
  (loop [cpu cpu
         pos 0]
    (case (nth cpu pos)
      1 (recur (process-cpu cpu pos +) (+ pos 4))
      2 (recur (process-cpu cpu pos *) (+ pos 4))
      99 (nth cpu 0)
      "def" (str "something went wrong - pos " pos ", str " (nth cpu pos)))))

;; loop through all different x-y pairs comparing against constant goal output
;; return either calculated program alarm value or 0 if goal not found
(defn find-program-alarm-val []
  (let [original-cpu (get-cpu)]
  (loop [x 0
         y 0]
      (if (= (process-opcode (set-alarm-state original-cpu x y)) goal-output)
        (calc-program-alarm x y)
        (if (= y 99)
          (if (= x 99)
            0
            (recur (+ x 1) 0))
          (recur x (+ y 1)))))))


;; get cpu, set up, process, then display first value
(defn -main [] (println (find-program-alarm-val)))
