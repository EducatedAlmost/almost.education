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

(defn lorem-ipsum []
  [:p "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."])

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
   :children [[light-mode-toggle] [logo] [title] [links]]])

(defmethod routes/panels :home-panel [] [home-panel])

;; About

(defn about-title [] [:h2 "About me"])

(defn link-to-home-page []
  [:div.home-link
   [:a {:href "/" :on-click #(re-frame/dispatch [::events/navigate :home])} "Home"]])

(defn about-content []
  [:div.content
   [:p "Hi, I'm Alex, a software developer and tutor from the United Kingdom."]
   [:p "E-mail me at " [:a {:href "mailto:EducatedAlmost@gmail.com"} "EducatedAlmost@gmail.com"]]
   [:p "You can have a look at the software I write on " [:a {:href "https://github.com/EducatedAlmost"} "GitHub"] "."]
   [:p "You can read the things I write on my " [:a {:href "https://blog.almost.education"} "blog"] "."]
    [:p "Watch my videos on " [:a {:href "https://www.youtube.com/channel/UCpTg2sXTOSTPmctaTMFCrGA"} "YouTube"] "."]
   [:p "Join my " [:a {:href "https://discord.gg/EJS23hE6vX"} "Discord"] "."]
   [:p "Browse my " [:a {:href "https://twitter.com/EducatedAlmost"} "Twitter"] "."]
   [:p "Find me on " [:a {:href "https://reddit.com/u/EducatedAlmost"} "Reddit"]
    ", look at my "
    [:a {:href "https://reddit.com/r/AlmostEducated"} "Subreddit"] "."]
   ])

(defn about-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[light-mode-toggle]
              [small-logo]
              [title]
              [link-to-home-page]
              [about-title]
              [about-content]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; Tutoring

(defn tutoring-title [] [:h2 "Tutoring"])

(defn tutoring-content []
  [:div.content
   [:p "On my "
    [:a {:href "https://discord.gg/EJS23hE6vX"} "Discord server "]
    " I freely help with Maths, Physics, and Programming, but I also work with my students as a tutor, teaching lessons a few times a week for an hour or so each."]
   [:p "I'm an experienced tutor, having taught students for over five years, and I am a graduate of the University of Cambridge, where I studied Physics and Astrophysics."]
   [:p "If you need help with Chemistry and Biology, you're in luck because I work closely with a tutor specialised in those subjects. He was first in his year graduating in Biochemistry from Bath University, and now works in Public Health. We teach students from ages 12–18, and I also teach maths to those studying a science at university. "]
   [:p "Because of the difficulty students have had this past year, we have dropped our rates to make our services as affordable as possible, so that no one should miss out on quality teaching. "]
   [:p "We charge £30/hour of tuition. In other currencies we charge $40, 55 AUD, 50 CAD. Lessons take place on Google Meet or Discord. I am signed up with Square to securely take card payments, and I only do so after the lesson has taken place. "]
   [:p "Should you be interested, please send me a DM on Discord (AlmostEducated#3211) or email me (EducatedAlmost@gmail.com), and I can provide you with more information and we can arrange convenient times to work together!"]
   ])

(defn tutoring-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[light-mode-toggle]
              [small-logo]
              [title]
              [link-to-home-page]
              [tutoring-title]
              [tutoring-content]]])

(defmethod routes/panels :tutoring-panel [] [tutoring-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :src      (at)
     :height   "100%"
     :children [(routes/panels @active-panel)]]))
