;; 0-based infinite Fibonacci sequence
(defn fib-seq []
  ((fn f [a b] (lazy-seq (cons a (f b (+ a b))))) 0 1))

;; 1-based infinite Fibonacci sequence
(defn fib-seq1 []
  (drop 1 (fib-seq)))

(defn fib [n]
  (take n (fib-seq)))

(defn fib1 [n]
  (take n (fib-seq1)))
