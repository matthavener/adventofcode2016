(ns aoc.day1
  (:require [clojure.string :as string]))

(def dirs
  [
   [1 0]
   [0 1]
   [-1 0]
   [0 -1]
   ]
  )

(defn vecs []
  (let [cmds (map (fn [c] [(first c)
                           (read-string (apply str (rest c)))])
                  (-> (slurp "day1.input") (string/split #" ")))
        [dir ret] (reduce
                    (fn [[cur-d ret] [d mag]]
                      (let [next-d (mod ((if (= d \R) dec inc) cur-d)
                                        (count dirs))]
                        [next-d (conj ret
                                      (mapv (partial * mag) (get dirs next-d)))]))
                    [0 []] cmds)

        ]
    ret
    ))

(defn solve1 []
  (let [vecs (vecs)]
    (+ (apply + (map first vecs) (apply + (map second vecs))))))

(defn unit [n]
  (cond
    (= n 0) 0
    (< n 0) -1
    :else 1))

(defn solve2 []
  (let [vecs (vecs)
        ex-vecs (mapcat (fn [v]
                          (repeat
                            (apply + (map #(Math/abs %) v))
                            (mapv unit v))) vecs)
        places (reductions #(mapv + %1 %2) [0 0] ex-vecs)
        duplicates (->> places
                        (frequencies)
                        (filter #(> (second %) 1))
                        (map first)
                        (into #{}))
        ]
    (apply + (map #(Math/abs %) (some duplicates places)))))

(comment
  (solve1)
  (solve2)
  )
