(define do-drive #t)

(define (init) #t)
(define (update)
  (when do-drive
    (when (drop)
      (set! do-drive #f))))
