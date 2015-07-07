# Gujtar

Gujtar is a weekend hack to learn Clojure and brush up on music theory at the same time.

## Usage

Let's start by checking out the chromatic scale.

```clojure
(notes/chromatic-scale)
; [:c :c# :d :d# :e :f :f# :g :g# :a :a# :b]

(notes/chromatic-scale :g)
; [:g :g# :a :a# :b :c :c# :d :d# :e :f :f#]
```

We can play around with intervals.

```clojure
(notes/interval :a :e)
; 7
(notes/add-interval :a 7)
; :e
```

OK, that's not really all that exciting. Let's move on to scales.
I have some notes, let's see which scales they're present in:

```clojure
(scales/search-partial [:c :d# :g :a#))
; ([:d# :major] [:g :harmonic-minor] [:d# :mixolydian] [:f :natural-minor] [:g# :major]
; [:g :natural-minor] [:d :ahava-raba] [:c :dorian] [:c :minor-pentatonic]
; [:c :natural-minor] [:f :dorian] [:a# :dorian] [:f :melodic-minor-desc] [:f :mixolydian]
; [:a# :melodic-minor-asc] [:a# :melodic-minor-desc] [:a# :major] [:a# :mixolydian]
; [:d# :melodic-minor-desc])
```

Wow, that's a whole bunch. I happen to know we're in the key of C, so
let's limit ourselves to that:

```clojure
(scales/search-partial [:c :d# :g :a#] (scales/in-key :c))
; ([:c :dorian] [:c :natural-minor] [:c :minor-pentatonic])
```

Those minor scales look promising. Let's check them out:

```clojure
(scales/get-scale :c :natural-minor)
; [:c :d :d# :f :g :g# :a#]
(scales/get-scale :c :minor-pentatonic)
; [:c :d# :f :g :a#]
```

Perfect. It's also possible to search for exact matches:

```clojure
(scales/search [:a :b :c :d :e :f :g])
; ([:d :dorian] [:a :natural-minor] [:e :phrygian] [:g :melodic-minor-desc] [:c :major] [:g :mixolydian] [:f :lydian])
```

We can do the same thing with chords:

```clojure
(chords/search [:c :e :g])
; ([:c :maj])
```

Maybe we can add some notes to spice it up a little. Let's see which
notes these chords take part in:

```clojure
(chords/search-partial [:c :e :g])
; ([:c :thirteenth] [:c :sixth-add-9] [:d :eleventh] [:a :min7] [:d :thirteenth-sus4]
; [:c :dom7-b9] [:c :maj9] [:d# :thirteenth-b9] [:a :min13] [:a :min9]
; [:c :thirteenth-b9] [:f :maj13] [:c :maj13] [:c :dom7-#9] [:e :dom7-#5-#9] [:c :maj]
; [:c :ninth] [:d :min11] [:c :maj7] [:c :sixth] [:a :min11] [:f :min9-maj7]
; [:g :thirteenth-sus4] [:d :ninth-sus4] [:c :ninth-#11] [:f :maj9] [:c :eleventh]
; [:c :maj7-#11] [:a :dom7-#9] [:c :add9] [:c :dom7] [:e :min-b6] [:c :dom7-#11])

(chords/search-partial [:c :e :g] (chords/in-key :c))
; ([:c :thirteenth] [:c :sixth-add-9] [:c :dom7-b9] [:c :maj9] [:c :thirteenth-b9]
; [:c :maj13] [:c :dom7-#9] [:c :maj] [:c :ninth] [:c :maj7] [:c :sixth]
; [:c :ninth-#11] [:c :eleventh] [:c :maj7-#11] [:c :add9] [:c :dom7] [:c :dom7-#11])
```

Allright, how about this one?

```clojure
(chords/get-chord :c :sixth-add-9)
; [:c :e :g :a :d]
```

We can also find all the chords that are in a given scale:

```clojure
(chords/search-in (scales/get-scale :a :minor-pentatonic))
; ([:c :sixth-add-9] [:c :third] [:a :min7] [:g :fifth] [:a :fifth] [:d :sus2] [:g :sus2]
; [:c :maj] [:d :dom7-sus4] [:c :sixth] [:a :min3] [:d :fifth] [:d :ninth-sus4] [:a :min]
; [:e :min3] [:a :sus4] [:d :sus4] [:g :sus4] [:a :dom7-sus4] [:c :add9] [:c :sus2]
; [:c :fifth])
```

Time to pick up the guitar. I wonder which notes are in a G major chord:

```clojure
(fingerings/notes [3 2 0 0 3 3])
; (:g :b :d :g :d :g)
```

I wonder what that chord I just played was:

```clojure
(chords/search (fingerings/notes [nil 3 2 0 3 0]))
; ([:c :add9])
```

How about this jazz chord?

```clojure
(chords/search (fingerings/notes [3 nil 4 4 3 nil]))
; ([:b :min-b6] [:g :maj7])
```

Alternate tunings work too:

```clojure
(fingerings/notes [3 3 3] [:c# :g# :c# :f# :a# :d#])
; (:e :b :e)
```

Of course, we're not limited to chords. I wonder what scale that solo I played was in:

```clojure
(scales/search (fingerings/notes [[0 3 5] [3 5 7] [5 7] [5 7 9] [8]]))
; ([:d :dorian] [:e :natural-minor] [:a :minor-pentatonic] [:d :natural-minor]
; [:a :natural-minor] [:f :major] [:c :melodic-minor-desc] [:d :melodic-minor-desc]
; [:g :melodic-minor-desc] [:g :melodic-minor-asc] [:c :major] [:g :mixolydian]
; [:a :dorian] [:g :dorian] [:c :mixolydian] [:d :mixolydian] [:g :major])
```

OK. I wonder what that pentatonic scale looks like on the fifth fret:

```clojure
(fingerings/scale-position (scales/get-scale :a :minor-pentatonic) 5)
; ((5 8) (5 7) (5 7) (5 7) (5 8) (5 8))
```

Going back to chords, we can figure out how to play them:

```clojure
(fingerings/chord (chords/get-chord :a :min))
; [nil 0 2 2 1 0]
```

It works even for alternative tunings:

```clojure
(fingerings/chord (chords/get-chord :a :sus2) [:d :a :d :f# :a :d])
; [nil 0 2 3 2 2]
```

You can get a range of possible fingerings with:

```clojure
(fingerings/all (chords/get-chord :g :maj7))
; ([3 2 0 0 3 2] [3 2 0 0 0 2] [3 2 4 4 3 2] [3 2 4 4 3 3] .. )

(fingerings/all-with-inversions (chords/get-chord :g :maj7))
; ([2 2 0 0 0 2] [2 2 0 0 3 3] [2 2 0 0 3 2] [3 2 0 0 3 2] .. )
```
