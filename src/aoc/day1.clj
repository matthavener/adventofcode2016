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

(defn solve []
  (let [cmds (map (fn [c] [(first c)
                           (read-string (apply str (rest c)))])
                  (-> (slurp "day1.input") (string/split #" ")))
        [dir ret] (reduce
                    (fn [[cur-d ret] [d mag]]
                      (let [next-d (mod ((if (= d \R) dec inc) cur-d)
                                        (count dirs))]
                        [next-d (conj ret (mapv (partial * mag) (get dirs next-d)))]))
                    [0 []] cmds)

        ]
    (+ (apply + (map first ret)) (apply + (map second ret)))))

(comment
  (solve)
  )
