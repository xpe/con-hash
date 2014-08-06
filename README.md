# con-hash

A basic Clojure implementation of [consistent hashing][1].

[1]: https://en.wikipedia.org/wiki/Consistent_hashing

Not all implementations of consistent hashing are the same. This one
is probably one of the simpler ones you'll find. It is designed to
provide a consistent hash given these bits of information:

* An item (e.g. a keyword, such as `:elephant`)
* An item hashing function (e.g. `hash`)
* A sequence of nodes (e.g. `["N1" "N2" "N3" "N4"]`)
* A node hashing function (e.g. `hash`)

## Usage

```clj
(require '[con-hash.core :refer [consistent-hash-fn]])
```

First, create a consistent hashing function using `consistent-hash-fn`. It
takes two arguments, an item hashing function and a node hashing function.

```clj
(def chf (consistent-hash-fn hash hash))
```

This gives `chf`, a reusable consistent hashing function. (Due to careful
thinking and painstaking effort, all of this possible without employing a
class called `AbstractConsistentHashingFunctionFactory`.)

[1]: https://en.wikipedia.org/wiki/MurmurHash

[2]: https://weblogs.java.net/blog/tomwhite/archive/2007/11/consistent_hash.html

[3]: http://problemsworthyofattack.blogspot.com/2007/10/mixing-with-md5.html

Here is how you use the hashing function:

```clj
(def nodes #{"N1" "N2" "N3" "N4"})
(chf "1A" nodes) ; "N3"
(chf "2B" nodes) ; "N3"
(chf "3C" nodes) ; "N1"
(chf "4D" nodes) ; "N4"
(chf "5E" nodes) ; "N3"
(chf "6F" nodes) ; "N4"
(chf "7G" nodes) ; "N4"
(chf "8H" nodes) ; "N3"
(chf "9I" nodes) ; "N3"
```

According to [Consistent Hashing by Tom White][2], "for consistent hashing to
be effective, it is important to have a hash function that [mixes][3] well."
For this example, we'll use the `hash` function. As of Clojure 1.6, `hash`
uses [Murmur hashing][1].

As you can see above, using `hash` is not mixing well for the above inputs.

What happens when we add more nodes?

```clj
(chf "item-42" nodes) ; "N3"
(def nodes (conj nodes "N5"))
(chf "item-42" nodes) ; "N5"
(def nodes (conj nodes "N6"))
(chf "item-42" nodes) ; "N6"
(def nodes (conj nodes "N7"))
(chf "item-42" nodes) ; "N6"
```

What happens when we remove a node?

```clj
(def nodes (disj nodes "N6"))
(chf "item-42" nodes) ; "N5"
```

## Related Projects

See also:

* [Consistent Hashing in Clojure by Shriphani Palakodety][CHiC], with a blog
  post and visualization.

[CHiC]: http://blog.shriphani.com/2014/05/01/consistent-hashing-in-clojure/

## License

Copyright 2014 Bluemont Labs LLC

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
