(ns aoc.day2
  (:require [clojure.string :as string]))

(defn readit [] (-> (slurp "day2.input")
                    (string/split #"\n")))

#_(defn readit [] ["ULL" "RRDDD" "LURDL" "UUUUD"])

(def digmap [[1 2 3]
             [4 5 6]
             [7 8 9]])

(def digmap2 [[nil nil 1 nil nil]
              [nil 2   3   4 nil]
              [5   6   7   8   9]
              [nil \A  \B  \C nil]
              [nil nil \D  nil nil]
              ])

(def move {\R [0 1]
           \L [0 -1]
           \U [-1 0]
           \D [1 0]
           })

(defn trunc+ [a b] (-> (+ a b)
                       (max 0)
                       (min 2)))

(defn todig [start dirs]
  (reduce #(mapv trunc+ %1 %2) start (map move dirs)))

(defn solve1 []
  (rest (map #(get-in digmap %1) (reductions todig [1 1] (readit))))
  )

(defn part2+ [v mv]
  (let [v' (mapv + v mv)]
    (if (get-in digmap2 v')
      v'
      v)))


(defn todig2 [start dirs]
  (reduce part2+ start (map move dirs)))

(defn solve2 []
  (rest (map #(get-in digmap2 %1) (reductions todig2 [2 0] (readit))))) 

(comment
  (trunc+ 1 2)
  (count (readit))
  (map move [\R\R\D\D])
  (todig [1 1] [\R\R\D\D])
  (solve1)

  (part2+ [2 4] [0 1])
  (todig2 [2 2] [\R\R])
  (solve2)
  )
