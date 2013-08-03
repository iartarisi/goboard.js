(ns goboard.goban
  (:require [goog.dom]
            [goog.events :as events])
  (:use [goboard.utils :only [indexed]]))

(def ^:const pixel 0.5)  ; hack to draw pixel-perfect lines
(def ^:const dot-radius 2)  ; radius of the 9 board dots

(def shadow (atom nil))

(defn mouse-location
  "Return the X and Y of a board intersection
   or nil if the event was out-of-bounds"
  [board event]
  (let [x (- (.-offsetX event) (.-offsetLeft (board :canvas)))
        y (- (.-offsetY event) (.-offsetTop (board :canvas)))
        square (board :space)
        half-square (/ square 2)]
    (if (and (> x (- square half-square))
             (< x (+ square (board :inner) half-square))
             (> y (- square half-square))
             (< y (+ square (board :inner) half-square)))
      [(Math/floor (/ (- x half-square) square))
       (Math/floor (/ (- y half-square) square))])))

(defn get-stone
  "Get the stone color at coordinates x, y"
  [board x y]
  (nth (board :stones) (+ x (* 19 y))))

(defn draw-background [board]
  (set! (. (board :context) -fillStyle) (board :background))
  (let [x 0
        y 0
        width (+ (board :inner) (* 2 (board :space)))
        height width]
    (. (board :context) (fillRect x y width height))))

(defn draw-lines [board]
  (.beginPath (board :context))
  (let [close-edge (board :offset)
        far-edge (+ (board :offset) (board :inner))]
    (doseq [x (take (board :lines)
                    (iterate (partial + (board :space))
                             (+ pixel (board :offset))))]
      (doto (board :context)
        ;; horizontal lines
        (.moveTo close-edge x)
        (.lineTo far-edge x)
        ;; vertical lines
        (.moveTo x close-edge)
        (.lineTo x far-edge))))
  (doto (board :context)
    (#(set! (.-strokeStyle %) (board :markings)))
    (.stroke)))

(defn draw-letters [board]
  (doto (board :context)
    (#(set! (.-textBaseline %) "top"))
    (#(set! (.-fillStyle %) (board :markings)))
    (#(dotimes [i (board :lines)]
        (. % (fillText (get "abcdefghjklmnopqrst" i)
                       (+ (* i (board :space)) (board :offset))
                       (+ (board :inner) (* (/ 5 4) (board :space)))))
        (. % (fillText (- (board :lines) i)
                       (+ (board :inner) (* (/ 5 4) (board :space)))
                       (+ (* i (board :space)) (board :offset))))))))

(defn draw-circle
  "Draw a circle on the board at the coordinates indicated by x and y."
  [board x y radius fill-color border-color]
  (doto (board :context)
    (.beginPath)
    (.arc (+ (board :offset) pixel (* x (board :space)))
          (+ (board :offset) pixel (* y (board :space)))
          radius 0 (* 2 (.-PI js/Math)) false)
    (.closePath)
    (#(set! (.-strokeStyle %) border-color))
    (#(set! (.-fillStyle %) fill-color))
    (.fill)
    (.stroke)))
  
(defn draw-dots
  "Draw standard goban dots in the nine points a.k.a. hoshi"
  [board]
  (doseq [[x y] [[3  3] [9  3] [15  3]
                 [3  9] [9  9] [15  9]
                 [3 15] [9 15] [15 15]]]
    (draw-circle board x y dot-radius (board :markings) (board :markings))))

(defn draw-stone
  "Draw a stone on the board.
   - color is one of: 1 - black, 2 - white
   - x and y are zero-indexed board coordinates starting from the top left
   corner of the board"
  [board color x y]
  (let [fill-color (if (= color 1) "black" "white")]
    (draw-circle board x y (board :stone-radius) fill-color "black")))

(defn draw-shadow
  [board shadow]
  (set! (.-globalAlpha (board :context)) 0.4)
  (let [[x y] shadow]
    (draw-stone board (board :playing) x y))
  (set! (.-globalAlpha (board :context)) 1))

(defn draw-last-move
  [board]
  (let [[x y] (board :last-move)]
    (draw-circle board x y dot-radius "red" "red")))

(defn draw-board
  [board]
  (draw-background board)
  (draw-lines board)
  (draw-letters board)
  (draw-dots board)
  (doseq [[i color] (indexed (board :stones))]
    (if (contains? #{1 2} color)
      (let [x (mod i (board :lines))
            y (quot i (board :lines))]
        (draw-stone board color x y))))
  (if (board :last-move)
    (draw-last-move board))
  (if @shadow
    (draw-shadow board @shadow)))

(defn mouse-move
  [board]
  (fn [event]
    (let [[X Y :as in-bounds] (mouse-location board event)
          [shadow-X shadow-Y] @shadow]
      (if (and in-bounds
               (or (not= X shadow-X)
                   (not= Y shadow-Y)))
        (do
          (reset! shadow [X Y])
          (draw-board board))))))

(defn mouse-out
  [board]
  (fn [event]
    (reset! shadow nil)
    (draw-board board)))

(defn mouse-up
  [board make-move]
  (fn [event]
    (let [location (mouse-location board event)]
      (if location
        (let [[X Y] location]
          (if (= 0 (get-stone board X Y))
            (make-move X Y)))))))

(defn setup-board
  [board make-move]
  (set! (.-height (board :canvas)) (board :size))
  (set! (.-width (board :canvas)) (board :size))
  (if (board :playing)
    (doto (.getElement goog.dom (board :canvas))
      (events/listen (.-MOUSEMOVE events/EventType) (mouse-move board))
      (events/listen (.-MOUSEOUT events/EventType) (mouse-out board))
      (events/listen (.-MOUSEUP events/EventType) (mouse-up board make-move)))))

(defn make-board
  "Draw a board with stones"
  [board make-move]
  (setup-board board make-move)
  (draw-board board))
