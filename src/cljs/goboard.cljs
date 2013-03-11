(ns goboard.goboard)

(.write js/document "<p>Hello, world!!</p>")

(def ^:const board-background-color "#E8BD68")
(def ^:const line-color "#444")
(def ^:const board-lines 19)
(def ^:const board-offset 20)
(def ^:const canvas-size 620)
(def ^:const inner-board (* (/ 9 10) canvas-size))
(def ^:const space-width (.floor js/Math (/ inner-board board-lines)))
(def ^:const board-margin (* (- board-lines 1) space-width))

; hack to draw pixel-perfect lines
(def ^:const pixel 0.5)

(def ^:const board-canvas
  (. js/document (getElementById "goBoard")))
(def ^:const board-ctx
  (. board-canvas (getContext "2d")))

(defn setup-board [canvas size]
  (set! (. canvas -height) size)
  (set! (. canvas -width) size))

(defn draw-background [context background-color space offset margin]
  (set! (. context -fillStyle) background-color)
  (let [x (- offset space)
        y x
        width (+ margin (* (/ 7 3) space))
        height width]
    (. context (fillRect x y width height))))

(defn draw-lines [context lines space offset margin color]
  (doseq [x (take lines
                  (iterate (partial + space)
                           (+ pixel offset)))]
    ; horizontal lines
    (. context (moveTo offset x))
    (. context (lineTo (+ margin offset) x))
    ; vertical lines
    (. context (moveTo x offset))
    (. context (lineTo x (+ margin offset))))
  (set! (. context -strokeStyle) color)
  (. context (stroke)))
  
(setup-board board-canvas canvas-size)
(draw-background board-ctx board-background-color space-width board-offset
                 board-margin)
(draw-lines board-ctx board-lines space-width board-offset board-margin
            line-color)