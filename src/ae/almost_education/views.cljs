(ns ae.almost-education.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [reagent.core :as reagent]
   [ae.almost-education.styles :as styles]
   [ae.almost-education.events :as events]
   [ae.almost-education.routes :as routes]
   [ae.almost-education.subs :as subs]
   [clojure.string]
   ["react-toggle" :as toggle]))

;; Light and dark mode

(defn light-mode-toggle []
  (let [light-mode? (re-frame/subscribe [::subs/light-mode?])]
    [:div#header-padding
     [:div#toggle.centre
      [(reagent/adapt-react-class toggle/default)
       {:default-checked @light-mode?
        :on-change #(re-frame/dispatch
                     [::events/light-mode-changed-to
                      (-> % (.-target) (.-checked))])}]]]))

(defn set-light-mode [light-mode?]
  (-> (.-body js/document)
      (.setAttribute "class" (if light-mode? "light" "dark"))))

(re-frame/reg-fx
 :light-mode?
 set-light-mode)

(defn logo []
  (let [light-mode? (re-frame/subscribe [::subs/light-mode?])
        image (clojure.string/join ["/img/ae-logo-" (if @light-mode? "dark-trans.png" "trans.png")])]
    [:div.centre [:img#home-logo {:src image}]]))

(defn small-logo []
  (let [light-mode? (re-frame/subscribe [::subs/light-mode?])
        image (clojure.string/join ["/img/ae-logo-" (if @light-mode? "dark-trans.png" "trans.png")])]
    [:div.centre [:img#other-logo {:src image}]]))

(defn title []
  [:h1 "AlmostEducated"])

;; Home

(defn links []
  [:div
   [:h2 [:a {:href "https://blog.almost.education"} "Blog"]]
   [:h2 [:a {:href "https://github.com/educatedalmost"} "Software"]]
   [:h2 [:a {:href "tutoring" :on-click #(re-frame/dispatch [::events/navigate :tutoring])} "Tutoring"]]
   [:h2 [:a {:href "about" :on-click #(re-frame/dispatch [::events/navigate :about])} "About"]]])

(defn home-panel []
  [re-com/v-box
   :src (at)
   :gap "1em"
   ;; :class (styles/home-box)
   :children [[light-mode-toggle]
              [logo]
              [title]
              [links]
              #_[link-to-about-page]]])

(defmethod routes/panels :home-panel [] [home-panel])

#_(defn link-to-about-page []
    [re-com/hyperlink
     :src      (at)
     :label    "go to About Page"
     :on-click #(re-frame/dispatch [::events/navigate :about])])

#_(defn home-title []
    (let [name (re-frame/subscribe [::subs/name])
          light-mode? (re-frame/subscribe [::subs/light-mode?])]
      [re-com/title
       :src   (at)
     ;; :label (str "Hello from " @name ". This is the Home Page.")
       :label "Almost Educated"
       :level :level1
       :class (styles/home-box)]))

#_(defn home-panel []
    [re-com/v-box
     :src      (at)
     :gap      "1em"
     :class (styles/home-box)
     :children [[light-mode-toggle]
                [logo]
                [home-title]
                [link-to-about-page]]])

;; About

(defn about-title []
  #_[re-com/title
     :src   (at)
     :label "This is the About Page."
     :level :level1]
  [:h2 "About"])

(defn link-to-home-page []
  [:a {:href "/" :on-click #(re-frame/dispatch [::events/navigate :home])} "Home"]
  #_[re-com/hyperlink
     :src      (at)
     :label    "go to Home Page"
     :on-click #(re-frame/dispatch [::events/navigate :home])])

(defn about-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[light-mode-toggle]
              [small-logo]
              [title]
              [link-to-home-page]
              [about-title]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; Tutoring

(defn tutoring-title [] [:h2 "Tutoring"])

(defn tutoring-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[light-mode-toggle]
              [small-logo]
              [title]
              [link-to-home-page]
              [tutoring-title]]])

(defmethod routes/panels :tutoring-panel [] [tutoring-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :src      (at)
     :height   "100%"
     :children [(routes/panels @active-panel)]]))
