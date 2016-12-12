(ns aoc.day5
  (:require [clojure.string :as string])
  (:import [java.security MessageDigest]))

(def digest (MessageDigest/getInstance "MD5"))

(defn md5 [s] (apply str (map #(format "%02x" (byte %)) (.digest digest (.getBytes s "UTF-8")))))

(defn good [s] (.startsWith (md5 s) "00000"))

(defn stream []
  (filter good (map #(str "reyedfim" %) (range))))

(defn find1 []
  (take 8 (stream)))

(def sol1 
  #_(find1)
  (list "reyedfim797564" "reyedfim938629" "reyedfim1617991" "reyedfim2104453"
        "reyedfim2564359" "reyedfim2834991" "reyedfim3605750" "reyedfim7183955"))

(defn solve1 [] (apply str (map #(get (md5 %) 5) sol1)))

(def sol2
  (list
    "reyedfim797564"
    "reyedfim938629"
    "reyedfim1617991"
    "reyedfim2104453"
    "reyedfim2564359"
    "reyedfim2834991"
    "reyedfim3605750"
    "reyedfim7183955"
    "reyedfim7292419"
    "reyedfim7668370"
    "reyedfim8059094"
    "reyedfim9738948"
    "reyedfim10098451"
    "reyedfim10105659"
    "reyedfim11395933"
    "reyedfim12187005"
    "reyedfim13432325"
    "reyedfim17274562"
    "reyedfim18101341"
    "reyedfim19897122"
    "reyedfim21475898"
    "reyedfim21671457"
    "reyedfim21679503"
    "reyedfim21842490"
    "reyedfim23036372"
    "reyedfim23090544"
    "reyedfim25067104"
    "reyedfim26815976"
    "reyedfim27230372"
    "reyedfim27410373"
    "reyedfim27514256"
    "reyedfim28688624"
    "reyedfim32680084"
    )
  )

(defn solve2 [] (->> (loop [s sol2 sol {}]
                  (let [found (md5 (first s))
                        pos (get found 5)
                        chr (get found 6)
                        sol' (if (or (not (#{\0 \1 \2 \3 \4 \5 \6 \7} pos)) (get sol pos)) sol (assoc sol pos chr))]
                    (if (or (empty? (rest s)) (= 8 (count sol')))
                      sol'
                      (recur (rest s) sol'))))
                    (sort-by first)
                    (map second)
                    (apply str)
                    ))

(comment
  (md5 "reyedfim797564")
  (solve1)
  )
