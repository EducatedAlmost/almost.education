(ns ae.almost-education.events
  (:require
   [re-frame.core :as re-frame]
   [ae.almost-education.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-fx
 ::navigate
 (fn-traced [_ [_ handler]]
            {:navigate handler}))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))

(defn handle-light-mode-changed [db checked?]
  (assoc db :light-mode? checked?))
;; (handle-light-mode-changed {:light-mode? false} true)

;; (re-frame/reg-event-fx
;;  ::light-mode-changed-to
;;  (fn-traced [{:keys [db] :as cofx} [_ checked?]]
;;             (let [new-db (handle-light-mode-changed db checked?)]
;;               (assoc cofx :db new-db))))

(re-frame/reg-event-fx
 ::light-mode-changed-to
 (fn-traced [{:keys [db] :as cofx} [_ checked?]]
            (let [new-db (assoc db :light-mode? checked?)]
              (assoc cofx
                     :light-mode? checked?
                     :db new-db))))
