(ns aoc.day6
  (:require [clojure.string :as string]))

(defn readit [] (string/split (slurp "day6.input") #"\n"))

(defn transform [rows]
  (apply mapv vector rows))

(defn solve1 []
  (apply str
         (map (fn [s] (->> s
                   (frequencies)
                   (sort-by second)
                   (last)
                   (first)
                   )) (transform (readit)))))


(defn solve2 []
  (apply str
         (map (fn [s] (->> s
                   (frequencies)
                   (sort-by second)
                   (ffirst)
                   )) (transform (readit)))))

