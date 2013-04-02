(ns goboard.goboard
  (:use [goboard.goban :only [draw-board draw-last-move]]))

(defn draw
  "Create the board struct and draw it
  - element-id - the DOM canvas id where the board will be drawn
  - stones - a 19x19 array with stone positions where each values is either:
    0 - empty, 1 - black stone, 2 - white stone
  Optional positional args:
  (if these are not given, then the last move will not be represented)
  - last-x - x coordonate of the last move
  - last-y - y coordonate of the last move"
  [element-id stones & [last-x last-y :as last-move]]
  (def ^:const board {:lines 19
                      :size 620
                      :offset 20
                      :space 28 ; (.floor js/Math (/ size (+ 3 board-lines)))
                      :inner 504  ; (* (- board-lines 1) space-width))
                      :stone-radius 11  ; space * 2/5
                      :canvas (. js/document (getElementById element-id))
                      :context (. (. js/document (getElementById element-id))
                                  (getContext "2d"))
                      :background "#E8BD68"
                      :markings "#444"
                      })
  (draw-board board stones)
  (if last-move
    (draw-last-move board last-x last-y)))