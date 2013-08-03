(ns goboard
  (:use [goboard.goban :only [make-board draw-last-move]]))

(defn ^:export draw
  "Create the board struct and draw it
  - element-id - the DOM canvas id where the board will be drawn
  - stones - a 19x19 array with stone positions where each values is one of:
    0 - empty, 1 - black stone, 2 - white stone
  Optional positional args:
  - playing - 1 (black) or 2 (white) indicates the current player's turn
  (if these are not given, then the last move will not be represented)
  - make-move - a callable that accepts two arguments X and Y which are
    0-indexed board coordinates of a valid click on the board
  - last-x - x coordonate of the last move
  - last-y - y coordonate of the last move"
  [element-id stones & playing make-move [x y :as last-move]]
  (def board {:lines 19
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
              :stones stones
              :playing playing
              :last-move last-move})

  (make-board board make-move))
