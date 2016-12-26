(ns aoc.day10
  (:require [clojure.string :as string]))

(defn readit [] (map
                  (fn [s]
                    (or
                      (if-let [s (re-find #"value (\d+) goes to bot (\d+)" s)]
                        {:type :start :value (read-string (s 1)) :bot (read-string (s 2))})
                      (if-let [s (re-find #"bot (\d+) gives low to (\w+) (\d+) and high to (\w+) (\d+)" s)]
                        {:type :give :bot (read-string (s 1))
                         :low-type (keyword (s 2)) :low (read-string (s 3))
                         :high-type (keyword (s 4)) :high (read-string (s 5))})
                      (throw (Exception. (str "could not parse " s)))))
                  (string/split (slurp "day10.input") #"\n")))

(defn infuse [st cmd]
  (case (:type cmd)
    :start (update-in st [:bot (:bot cmd) :values] conj (:value cmd))
    :give (assoc-in st [:bot (:bot cmd) :cmd] cmd)
    )
  )

(defn proc [st id bot]
  (if (= (count (:values bot)) 2)
    (let [[low-val high-val] (sort (:values bot))
          {:keys [low low-type high high-type]} (:cmd bot)]
      #_(println bot)
      (assert (= 2 (count (:values bot))))
      (if (= #{17 61} (into #{} (:values bot))) (println "found " [id bot]))
      (-> st
          (assoc-in [:bot id :values] [])
          (update-in [low-type low :values] conj low-val)
          (update-in [high-type high :values] conj high-val)
          ))
    st))

(defn step [st]
  (if-let [[id bot] (first (filter #(-> % second :values count (= 2)) (:bot st)))]
    (proc st id bot)))

(defn resolve []
  (let [steps (take-while identity (iterate step (reduce infuse {} (readit))))
        l (last steps)]
    (apply * (mapcat #(get-in l [:output % :values]) (range 0 3)))
    )
  )


(comment
  )


