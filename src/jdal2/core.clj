(ns jdal2.core
  (:import com.alibaba.druid.pool.DruidDataSource))

(def datasource_1  
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "192.168.195.139/eptid_promotion_dev"
   :user "root"
   :password "123456"})

(defn datasource_1_pool
  [datasource]
  (let [dspl (doto (DruidDataSource.)
               (.setDriverClass (:classname datasource))
               (.setJdbcUrl (str "jdbc:" (:subprotocl datasource) ":" (:subname datasource)))
               (.setUser (:user datasource))
               (.setPassword (:password datasource)))]
    {:datasourde dspl}))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
