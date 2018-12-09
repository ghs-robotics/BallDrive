(define do-drive #t)

(define (init) #t)
(define (update)
  (when do-drive
    (when (drop 3)
      (set! do-drive #f))))
