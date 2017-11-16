(ns jdal.core
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import com.alibaba.druid.pool.DruidDataSource))

(def db-config 
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//192.168.195.139/eptid_promotion_dev?useUnicode=true&characterEncoding=UTF8"
   :user "root"
   :password "123456"})

(defn- load-db-config [db-config-name]
  "Load db config from edn file."
  (with-open [in-edn (->
                       db-config-name
                       io/resource
                       io/reader
                       (java.io.PushbackReader.))]
    (edn/read in-edn)))

(defn- pooled-ds 
  [db-config]
  (let [dspl (doto (DruidDataSource.)
               (.setDriverClassName (:driver db-config))
               (.setUrl (:url db-config))
               (.setUsername (:user db-config))
               (.setPassword (:password db-config)))]
    {:datasource dspl
     :id (:id db-config)}))

(defn- init-datasources
  "Initialise datasource."
  [db-configs]
  (map pooled-ds db-configs))

(def ^:private datasources (init-datasources (load-db-config "db-config.edn")))

(defn- find-datasource-id
  [field-value datasources]
  (+ (mod (hash field-value) (count datasources)) 1))

(defn- find-table-id
  [field-value table-count]
  (mod (hash field-value) table-count))

(def- parse-field-value
  [field-name sql])

(println (find-datasource-id "test" datasources))

(def ^:private pooled-datasource (delay (pooled-ds db-config)))
(defn- db-connection [] @pooled-datasource)
;;----------------------
;(println (jdbc/query (db-connection) ["select * from coupon_info limit 1"]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
