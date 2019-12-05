(ns day3.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))

(def line1file "day3.txt")
(def line2file "day3v2.txt")

(defn read-item [item]
  (let [[dir & dist] item]
    [(keyword (str dir)) (Integer/parseInt (apply str dist))]))

(defn get-line-from-file [file]
  (map read-item
       (str/split (str/trim (slurp (io/resource file))) #",")))

(defn manhattan [[x y]] (+ (Math/abs x) (Math/abs y)))

(defn line [start-point [dir dist]]
  (let [new-point (case dir
                    :R (update start-point 0 #(+ % dist))
                    :L (update start-point 0 #(- % dist))
                    :U (update start-point 1 #(+ % dist))
                    :D (update start-point 1 #(- % dist)))
        [[x X] [y Y]] (map #(sort [%1 %2]) start-point new-point)]
    (cond-> (for [xi (range x (inc X))
                  yi (range y (inc Y))]
              [xi yi])
            (contains? #{:D :L} dir) reverse
            true rest)))

(defn create-path [cur-line]
  (-> (reduce
            (fn [{:keys [current] :as end} current-vector]
              (let [l (line current current-vector)]
                (-> end
                      (update :path into l)
                      (assoc :current (last l)))))
            {:path [[0 0]] :current [0 0]}
            cur-line)
      :path))

(defn process-lines []
 (apply min (map manhattan (set/difference (set/intersection
    (set (create-path (get-line-from-file line1file)))
    (set (create-path (get-line-from-file line2file)))) #{[0 0]}))))

(defn -main
  []
  (println (process-lines)))
