(ns goboard.goboard
  (:use [goboard.goban :only [draw-board draw-last-move]]))

(def ^:const board {:lines 19
                    :size 620
                    :offset 20
                    :space 28 ; (.floor js/Math (/ size (+ 3 board-lines)))
                    :inner 504  ; (* (- board-lines 1) space-width))
                    :stone-radius 11  ; space * 2/5
                    :canvas (. js/document (getElementById "goBoard"))
                    :context (. (. js/document (getElementById "goBoard"))
                                (getContext "2d"))
                    :background "#E8BD68"
                    :markings "#444"
                    })
(def stones [0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2
             0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
             0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
             0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1])

(draw-board board stones)

(draw-last-move board 18 18)