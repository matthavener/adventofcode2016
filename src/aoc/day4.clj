(ns aoc.day4
  (:require [clojure.string :as string]))

(defn readit [] (as-> (slurp "day4.input") d
                  (string/replace d "]" "")
                  (string/split d #"\n")
                  (map #(string/split % #"\[") d)))

(defn mycomp [[c1 f1] [c2 f2]]
  (let [fc (compare f2 f1)]
    (if (= 0 fc)
      (compare c1 c2)
      fc
      )))

(defn realroom? [[s top]]
  (= top
     (apply str (map first (->> (string/replace s #"[^a-z]" "")
                               frequencies
                               (sort mycomp)
                               (take 5)
                               )))))


(defn secid [s]
  (Integer. (string/replace s #"[^0-9]" "")))

(defn solve1 []
  (reduce + (map #(secid (first %)) (filter realroom? (readit)))))

(defn rot-letter [l c]
  (char (+ (int \a) (mod (+ c (- (int l) (int \a))) 26))))

(defn decrypt [[s _]]
  (let [si (secid s)]
    (apply str (map #(if (= \- %) \space (rot-letter % si)) s))))

(defn solve2 []
  (filter #(.contains (decrypt %) "north") (filter realroom? (readit)))
  )

(comment
  (realroom? ["aaaaa-bbb-z-y-x-123" "abxyz"])
  (realroom? ["a-b-c-d-e-f-g-h-987" "abcde"])
  (realroom? ["hqcfqwydw-fbqijys-whqii-huiuqhsx-660" "qhiwf"])

  (rot-letter \y 3 )
  (decrypt ["qzmt-zixmtkozy-ivhz-343"])
  (readit)
  (solve1)
  )
