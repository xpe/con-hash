(defproject con-hash "0.1.0"
  :description "Consistent hashing in Clojure."
  :url "http://github.com/bluemont/con-hash"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles
  {:dev
   {:source-paths ["dev"]
    :dependencies [[org.clojure/tools.namespace "0.2.5"]]}})
