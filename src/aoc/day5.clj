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

(defn solve2 [] (loop [s (stream) sol {}]
                  (let [found (first s)
                        _ (println found)
                        pos (get found 5)
                        chr (get found 6)
                        sol' (if (get sol pos) sol (assoc sol pos chr))]
                    (if (= 8 (count sol'))
                      sol'
                      (recur (rest s) sol')))))

(comment
  (md5 "reyedfim797564")
  (solve1)
  )
