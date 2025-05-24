;; reverse without using reverse
(defn rev [coll]
  (cond
    (vector? coll) (vec (rseq coll))
    (or (sorted? coll) (indexed? coll)) (into [] (rseq coll))
    :else (reduce cons '() coll)))
