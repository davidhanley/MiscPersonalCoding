(ns clothes.core
  (:gen-class))

(defn l[] (use 'clothes.core :reload))

(def clothes-orders [ [:socks :shoes]; :boots]
                      [:undershirt :shirt :vest]; :jacket]
		      [:underwear :pants]
		      [:pants :shoes]; :boots]
		      [:undershirt :hat] ] )

(def clothes-list (apply sorted-set (apply concat clothes-orders)))

(defn permute[ lst filter? ]
  (if (< (count lst) 2) [lst]
    (filter filter? (mapcat (fn[elt](map (fn[tail](cons elt tail)) (permute (remove #{elt} lst) filter?))) lst))))

(defn indices-follow-rules[ dress-order rule ]
  (apply < (remove #{-1} (map (fn[item](.indexOf dress-order item)) rule))))			    

(defn clothing-sequence-follows-all-rules[ clothing-sequence ]
  (every? true? (map (partial indices-follows-rule clothing-sequence) clothes-orders)))

(defn solve[]
  (count (filter clothing-sequence-follows-all-rules (permute clothes-list clothing-sequence-follows-all-rules))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
