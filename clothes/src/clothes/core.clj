(ns clothes.core
    (:gen-class))				      

(defn l[] (use 'clothes.core :reload))

(def clothes-orders [ [:socks :shoes :boots]
                      [:undershirt :shirt :vest :jacket]
		      [:underwear :pants :snowpants]
		      [:pants :shoes :boots]
		      [:undershirt :hat] 
		      [:jacket :scarf ] 
		      [:shoes :mittens] 
		      [:jacket :mittens] ] )

(def clothes-list (apply sorted-set (apply concat clothes-orders)))

(defn pair-follows-rule[ i1 i2 rule ] 
  (let [f #(let [i (.indexOf rule %)] (if (= i -1) nil i))
       p1 (f i1) p2 (f i2)]
	(if (and p1 p2) (< p1 p2) true)))

(defn pair-follows-every-rule[ i1 i2 ]
  (every? (partial pair-follows-rule i1 i2) clothes-orders))
(def pair-follows-every-rule (memoize pair-follows-every-rule))

(defn is-before-every[ item lst ]
  (every? (partial pair-follows-every-rule item) lst))

(defn permute[ lst ]
  (if (< (count lst) 2) [lst]
    (mapcat (fn[item] (let [rest-list (remove #{item} lst)]
			   (when (is-before-every item rest-list) 
			     (map (fn[rl](cons item rl)) (permute rest-list))))) lst)))

(defn solve[]
  (count (permute clothes-list)))

