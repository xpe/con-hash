(ns con-hash.core)

(defn clockwise-node
  "Returns the next clockwise node starting from hashed-item. The
  term 'clockwise' means 'greater than with wrap-around'."
  [hashed-item hashed-nodes]
  {:pre [(map? hashed-nodes) (sorted? hashed-nodes)]}
  (let [kv (->> hashed-nodes
                (filter (fn [[k v]] (< hashed-item k)))
                first)]
    (second (or kv (first hashed-nodes)))))

(defn hash-nodes
  "Returns a sorted map (where the key is a hash and the value is the
  original node) from a list of nodes and node hashing function."
  [nodes hash-fn]
  (->> nodes
       (map (fn [n] [(hash-fn n) n]))
       (into (sorted-map))))

(defn consistent-hash-fn
  "Returns a consistent hash function for a given item hashing
  function and node hashing function. The returned function accepts
  an item and sequence of nodes and returns one of the nodes."
  [item-hash-fn node-hash-fn]
  {:pre [(fn? item-hash-fn) (fn? node-hash-fn)]}
  (fn [item nodes]
    (let [hashed-nodes (hash-nodes nodes node-hash-fn)]
      (clockwise-node (item-hash-fn item) hashed-nodes))))
