(ns goboard.goboard)

(def ^:const board {:lines 19
                    :size 620
                    :offset 20
                    :space 28 ; (.floor js/Math (/ size (+ 3 board-lines)))
                    :inner 504  ; (* (- board-lines 1) space-width))
                    :canvas (. js/document (getElementById "goBoard"))
                    :context (. (. js/document (getElementById "goBoard"))
                                (getContext "2d"))
                    :background "#E8BD68"
                    :markings "#444"
                    })

; hack to draw pixel-perfect lines
(def ^:const pixel 0.5)

(defn setup-board [board]
  (set! (. (board :canvas) -height) (board :size))
  (set! (. (board :canvas) -width) (board :size)))

(defn draw-background [board]
  (set! (. (board :context) -fillStyle) (board :background))
  (let [x 0
        y 0
        width (+ (board :inner) (* 2 (board :space)))
        height width]
    (. (board :context) (fillRect x y width height))))

(defn draw-lines [board]
  (doseq [x (take (board :lines)
                  (iterate (partial + (board :space))
                           (+ pixel (board :offset))))]
    ;; horizontal lines
    (. (board :context) (moveTo (board :offset) x))
    (. (board :context) (lineTo (+ (board :inner) (board :offset)) x))
    ;; vertical lines
    (. (board :context) (moveTo x (board :offset)))
    (. (board :context) (lineTo x (+ (board :inner) (board :offset)))))
  (set! (. (board :context) -strokeStyle) (board :markings-color))
  (. (board :context) (stroke)))

(defn draw-letters [board]
  (set! (. (board :context) -textBaseline) "top")
  (set! (. (board :context) -fillStyle) (board :markings))
  (dotimes [i (board :lines)]
    (. (board :context) (fillText (get "abcdefghjklmnopqrst" i)
                                  (+ (* i (board :space)) (board :offset))
                                  (+ (board :inner) (board :space))))
    (. (board :context) (fillText (- (board :lines) i)
                                  (+ (board :inner) (board :space))
                                  (+ (* i (board :space)) (board :offset))))))
(setup-board board)
(draw-background board)
(draw-lines board)
(draw-letters board)