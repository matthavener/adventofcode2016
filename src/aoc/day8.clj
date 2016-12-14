(ns aoc.day8
  (:require [clojure.string :as string]))

(defn readit [] (map
                  (fn [s]
                    (or
                      (if-let [s (first (re-seq #"rect (\d+)x(\d+)" s))]
                        {:type :rect :x (read-string (s 1)) :y (read-string (s 2))})
                      (if-let [s (first (re-seq #"rotate column x=(\d+) by (\d+)" s))]
                        {:type :col :x (read-string (s 1)) :by (read-string (s 2))})
                      (if-let [s (first (re-seq #"rotate row y=(\d+) by (\d+)" s))]
                        {:type :row :by (read-string (s 2)) :y (read-string (s 1))})
                      (throw (Exception. (str "could not parse " s)))))
                  (string/split (slurp "day8.input") #"\n")))

(defn rotate [v n] (into [] (concat (subvec v (- (count v) n))
                                    (subvec v 0 (- (count v) n)))))

(defn transform [rows]
  (apply mapv vector rows))

(defn ex [m cmd]
  (case (:type cmd)
        :rect (reduce #(assoc-in %1 %2 1) m (for [x (range (:x cmd)) y (range (:y cmd))] [y x]))
        :row (update m (:y cmd) rotate (:by cmd))
        :col (transform (update (transform m) (:x cmd) rotate (:by cmd)))
        :default nil
        )
  )

(defn solve1 []
  (reduce + (flatten (reduce ex (into [] (take 6 (repeatedly #(into [] (take 50 (repeat 0)))))) (readit))))
  )

(defn solve2 []
  (map #(println (map {1 \# 0 \space} %))  (reduce ex (into [] (take 6 (repeatedly #(into [] (take 50 (repeat 0)))))) (readit)))
  )

(comment
  (reductions ex (into [] (take 6 (repeatedly #(into [] (take 50 (repeat 0)))))) (readit))

  (rotate [1 2 3 4] 1) ; [4 1 2 3]
  (rotate [1 2 3 4] 2) ; [3 4 1 2]
  (rotate [1 2 3 4] 3) ; [2 3 4 1]
  (transform (transform [[1 2 3] [4 5 6] [7 8 9]]))

  (ex [[0 0 0 0] [0 0 0 0] [4 0 0 0]] {:type :rect :x 3 :y 2})
  (ex [[4 0 0 0] [1 2 3 0] [4 0 0 0]] {:type :row :y 2 :by 1})
  (ex [[4 1 0 0] [0 2 0 0] [0 3 4 0]] {:type :col :x 1 :by 2})

  )


