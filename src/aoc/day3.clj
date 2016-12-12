(ns aoc.day3
  (:require [clojure.string :as string]))

(defn readit [] (as-> (slurp "day3.input") d
                    (string/split d #"\n")
                    (map #(read-string (str "[" % "]")) d)
                    ))

(defn transform [rows]
  (mapcat #(apply mapv vector %) (partition 3 rows)))

(defn valid? [[a b c]]
  (and
    (< a (+ b c))
    (< b (+ a c))
    (< c (+ a b))))

(defn solve1 [] (count (filter valid? (readit))))

(defn solve2 [] (count (filter valid? (transform (readit)))))

(comment

  (readit)
  (valid? [5 10 25])
  (valid? [2 2 1])
  (valid? [517 881 109])
  (solve1)
  (solve2)
  )
