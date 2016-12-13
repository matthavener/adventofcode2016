(ns aoc.day7
  (:require [clojure.string :as string]))

(defn readit [] (string/split (slurp "day7.input") #"\n"))

(defn ana? [s]
  (and (= (seq s) (reverse s))
       (< 1 (count (into #{} s)))))

(defn contains-ana? [s]
  (some #(ana? (subs s % (+ % 4))) (range 0 (- (count s) 3))))

(defn good [s]
  (and
    (some contains-ana? (string/split s #"\[[a-z]+\]"))
    (not (some contains-ana? (map second (re-seq #"\[([a-z]+)\]" s))))))

(defn solve1 []
  (count (filter good (readit))))

(defn find-ana [s]
  (filter ana? (map #(subs s % (+ % 3)) (range 0 (- (count s) 2)))))

(defn pair? [a b]
  (= (seq (subs a 0 2)) (reverse (subs b 0 2))))

(defn any-pair? [s1 s2]
  (some #(some (partial pair? %) (mapcat find-ana s1)) (mapcat find-ana s2)))

(defn solve2 []
  (count (filter
           (fn [s]
             (any-pair? (string/split s #"\[[a-z]+\]")
                        (map second (re-seq #"\[([a-z]+)\]" s))))
           (readit))))


(comment
  (good "ioxxoj[asdfgh]zxcvbn")
  (good "oiwjefoiwjeabba")
  (good "abbaoiwjefoiwjeaba")
  (good "abbao[abba]iwjefoiwjeaba")
  (good "abba")
  (good "oiwjefoiwjeaaaa")

  (find-ana "zazbz")

  (any-pair? "")

  (pair? "aba" "bab")
  (pair? "aba" "aba")
  ()

  )


