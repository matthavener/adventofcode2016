(ns aoc.day9
  (:require [clojure.string :as string]))

(defn readit [] (slurp "day9.input"))


(defn decode [input]
  (apply str
         (-> (loop [s (seq input) r '[]]
               (case (first s)
                 nil r
                 \( (let [[m len times] (re-find #"\((\d+)x(\d+)\)" (apply str s))
                          ;_ (println m len times)
                          substr (subs (apply str (drop (count m) s)) 0 (read-string len))
                          ;_ (println "substr:" substr)
                          exp-str (apply str (repeat (read-string times) substr))
                          ;_ (println "exp-str:" exp-str)
                          ]
                      (recur (drop (+ (count m) (count substr)) s) (conj r exp-str)))
                 (recur (rest s) (conj r (str (first s))))))
             (flatten)
             )))


(defn decode2 [input]
  (loop [s (seq input) r 0]
    (case (first s)
      nil r
      \( (let [[m len times] (re-find #"\((\d+)x(\d+)\)" (apply str s))
               ;_ (println m len times)
               substr (subs (apply str (drop (count m) s)) 0 (read-string len))
               ;_ (println "substr:" substr)
               exp-str-len (* (read-string times) (decode2 substr))
               ;_ (println "exp-str-len:" exp-str-len)
               ]
           (recur (drop (+ (count m) (count substr)) s) (+ r exp-str-len)))
      (recur (rest s) (inc r)))
    ))

(comment
  (decode "A(2x2)BCD(2x2)EFG")
  (decode "(6x1)(1x3)A")
  (count (decode (readit)))
  (decode2 (readit))
  )


