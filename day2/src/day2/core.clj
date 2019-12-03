(ns day2.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(def filename "day2pt1input.txt")

(defn get-cpu [] (str/split (slurp (io/resource filename)) #",") )

(defn process-opcode [set]
  (loop [set set
         pos 0]
    (case (get set pos)))

(defn -main [] (process-opcode get-cpu))
