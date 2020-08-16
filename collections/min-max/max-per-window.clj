;; Semi-dynamic algorithm for finding the maximum of each collection "window"
;; of the specified length, in sequential order with overlapping.
;; TODO: clean up the code (my first Clojure program!)

(defn max-per-window [coll window-size]
  (defn next-window [processed-window-amnt] 
    (if (= processed-window-amnt 0)
      (vec (take window-size coll))
      (let [coll-rest (take window-size (drop processed-window-amnt coll))]
        (if (< (count coll-rest) window-size)
            []
            (vec coll-rest)))))

  (defn max-of-window [window res]
    (defn calc-max [] (apply max window))
    (if (empty? res)
      (calc-max)
      (let [new-item (last window)
            last-res (last res)]
            (if (> new-item last-res)
              new-item
              (calc-max)))))

  (loop [window (next-window 0)
         res []
         processed 1]
    (if (empty? window)
        res
        (recur (next-window processed)
               (conj res (max-of-window window res))
               (inc processed)))))
        
(let [coll [12 34 5 7 99 100 15 5 -1000 500]
      window-size 3
      expected [34 34 99 100 100 100 15 500]]
      (println (max-per-window coll window-size)))
