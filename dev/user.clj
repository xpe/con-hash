(ns user
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.repl :refer :all]
    [clojure.test :refer [run-tests run-all-tests]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [con-hash.core :refer :all]))

(defn md5-hash
  "Returns a MD5 hash (as Clojure BigInt) of the input string."
  [s]
  (let [md (java.security.MessageDigest/getInstance "MD5")]
    (bigint (.digest md (.getBytes s)))))

(defn md5-hash-hex
  "Returns a hexadecimal string MD5 hash of the input string."
  [s]
  (let [md (java.security.MessageDigest/getInstance "MD5")]
    (.toString (BigInteger. (.digest md (.getBytes s))) 16)))
