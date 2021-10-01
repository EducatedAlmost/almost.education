(ns ae.almost-education.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [reagent.core :as reagent]
   [ae.almost-education.styles :as styles]
   [ae.almost-education.events :as events]
   [ae.almost-education.routes :as routes]
   [ae.almost-education.subs :as subs]
   ["react-toggle" :as toggle]))

;; home

(defn home-title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :src   (at)
     :label (str "Hello from " @name ". This is the Home Page.")
     :level :level1
     :class (styles/level1)]))

(defn link-to-about-page []
  [re-com/hyperlink
   :src      (at)
   :label    "go to About Page"
   :on-click #(re-frame/dispatch [::events/navigate :about])])

;; Dark and light

(defn light-mode-toggle []
  (let [light-mode? (re-frame/subscribe [::subs/light-mode?])]
    [:div [:p "Hello"]
     [(reagent/adapt-react-class toggle/default)
      {:default-checked @light-mode?
       :on-change #(do (println "Toggling")
                       (re-frame/dispatch
                        [::events/light-mode-changed-to
                         (-> % (.-target) (.-checked))]))}]]))

(defn set-light-mode [light-mode?]
  (do (println "Setting")
      (-> (.-body js/document)
          (.setAttribute "class" (if light-mode? "dark" "light")))))

(re-frame/reg-fx
 :light-mode?
 set-light-mode)

(defn home-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[home-title]
              [link-to-about-page]
              [light-mode-toggle]]])

(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-title []
  [re-com/title
   :src   (at)
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink
   :src      (at)
   :label    "go to Home Page"
   :on-click #(re-frame/dispatch [::events/navigate :home])])

(defn about-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[about-title]
              [link-to-home-page]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :src      (at)
     :height   "100%"
     :children [(routes/panels @active-panel)]]))
