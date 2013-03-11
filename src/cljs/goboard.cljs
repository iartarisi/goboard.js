(ns goboard.goboard)

(def ^:const board {:lines 19
                    :size 620
                    :offset 20
                    :inner 558  ; (* (/ 9 10) canvas-size)
                    :space 29  ; (.floor js/Math (/ inner-board board-lines))
                    :margin 522  ; (* (- board-lines 1) space-width))
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
  (let [x (- (board :offset) (board :space))
        y x
        width (+ (board :margin) (* (/ 7 3) (board :space)))
        height width]
    (. (board :context) (fillRect x y width height))))

(defn draw-lines [board]
  (doseq [x (take (board :lines)
                  (iterate (partial + (board :space))
                           (+ pixel (board :offset))))]
    ; horizontal lines
    (. (board :context) (moveTo (board :offset) x))
    (. (board :context) (lineTo (+ (board :margin) (board :offset)) x))
    ; vertical lines
    (. (board :context) (moveTo x (board :offset)))
    (. (board :context) (lineTo x (+ (board :margin) (board :offset)))))
  (set! (. (board :context) -strokeStyle) (board :markings-color))
  (. (board :context) (stroke)))

(setup-board board)
(draw-background board)
(draw-lines board)