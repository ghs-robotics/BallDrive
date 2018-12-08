(define drive-mode 0)

(define (init) #t)
(define (update)
  (cond
    [(= drive-mode 0)
      (when (path 0 0 1000 0 1000 100 0.1 0 0 1000 0 1000 100 0.1)
        (set! drive-mode 1))]
    
    [(= drive-mode 1)
      (when (path 0 0 -1000 0 1000 100 0.1 0 0 -1000 0 1000 100 0.1)
        (set! drive-mode 2))]
))
