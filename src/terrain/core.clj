(ns terrain.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/smooth 8)
  (q/frame-rate 60)
  (q/color-mode :hsb)

  {:position {:y 0}})

(defn update-state [state]
  (-> state
    (update-in [:position :y] inc)))

(defn draw-state [state]
  (q/background 0)
  (q/stroke 255)
  (q/fill 0)

  (q/translate 0 200 0)
  (q/rotate-x (/ q/PI 3))

  (let [scale       50
        rows        100
        columns     100
        noise-scale (/ 0.005)
        noise-mag   100
        speed 6
        noise
        (fn [x y]
          (q/map-range
            (q/noise
              (/ x noise-scale)
              (/ (+ y (* speed (-> state :position :y))) noise-scale))
            0 1
            (- noise-mag) noise-mag))]
    (doseq [column (range columns)]
      (q/begin-shape :triangle-strip)
      (doseq [row (range rows)]
        (let [x  (* scale column)
              y  (* scale row)
              xn (+ x scale)
              z (noise x y)
              zn (noise xn y)]
          (q/vertex x y z)
          (q/vertex xn y zn)))
      (q/end-shape))))


(comment

  (q/defsketch terrain
    :title "You spin my circle right round"
    :size [1000 1000]
    :renderer :p3d
    :setup setup
    :update update-state
    :draw draw-state
    :features [:keep-on-top]
    :middleware [m/fun-mode])

  )
