(defn size [lst]
  (reduce + (map (constantly 1) lst)))
