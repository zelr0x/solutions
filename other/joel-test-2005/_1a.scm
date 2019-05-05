(define (accumulate combiner null-value arr) 
   (if (null? arr)
       null-value
       (combiner (car arr)
                 (accumulate combiner
                             null-value
                             (cdr arr)))))

(define (sum-of-squares arr)
    (accumulate(lambda (x y) (+ (* x x) y)) 0 arr))

(sum-of-squares '(1 2 3 4 5))
