(ns ae.almost-education.styles
  (:require-macros
   [garden.def :refer [defcssfn]])
  (:require
   [spade.core   :refer [defglobal defclass]]
   [garden.units :refer [deg px]]
   [garden.color :refer [rgba]]))

#_(defclass dark []
    {:color "#debede"
     :background-color "#010022"})

#_(defclass light []
    {:color "#010022"
     :background-color "#debede"})

#_(defcssfn linear-gradient
    ([c1 p1 c2 p2]
     [[c1 p1] [c2 p2]])
    ([dir c1 p1 c2 p2]
     [dir [c1 p1] [c2 p2]]))

#_(defglobal defaults
    [:body
     {:color               :red
      :background-color    :#ddd
      :background-image    [(linear-gradient :white (px 2) :transparent (px 2))
                            (linear-gradient (deg 90) :white (px 2) :transparent (px 2))
                            (linear-gradient (rgba 255 255 255 0.3) (px 1) :transparent (px 1))
                            (linear-gradient (deg 90) (rgba 255 255 255 0.3) (px 1) :transparent (px 1))]
      :background-size     [[(px 100) (px 100)] [(px 100) (px 100)] [(px 20) (px 20)] [(px 20) (px 20)]]
      :background-position [[(px -2) (px -2)] [(px -2) (px -2)] [(px -1) (px -1)] [(px -1) (px -1)]]}])

#_(defclass level1
    []
    {:color :green})
