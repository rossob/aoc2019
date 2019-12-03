(ns day2.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(def filename "day2pt1input.txt")

;; read intcode string to vector of ints
(defn get-cpu []
  (vec (map read-string (str/split (slurp (io/resource filename)) #","))))

;; stated in problem for setup -
;; replace position 1 with the value 12 and replace position 2 with the value 2
(defn set-alarm-state [cpu]
  (assoc (assoc cpu 2 2) 1 12))

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

;; get cpu, set up, process, then display first value
(defn -main [] (println (process-opcode (set-alarm-state (get-cpu)))))
