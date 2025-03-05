package datastructure.multiset;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * This class permits to implement a multiset data structure.
 *
 * A multiset is a data structure similar to a {@link java.util.Set} but where
 * the elements have a cardinality that can be greater than one. Conversely, a
 * set can be seen as a multiset where the elements have a cardinality at most
 * one.
 *
 *
 * @param <T> The basic type to be used in the multiset
 * @author Sergey Verlan
 * @version 1.0
 */
public class HashMultiset<T> implements Iterable<Entry<T, Integer>> {

    private HashMap<T, Integer> contents = new HashMap<T, Integer>();
    private HashMap<T, Integer> markedcontents = null;
    /**
     * This variable permits to control {@link #toString()} method. If set to
     * false, then a multiset containing 2 elements a would be output as [a,2].
     * If set to true then the same multiset is output as a a. The default value
     * is false.
     */
    public boolean expandMultiset = false;
    private boolean shouldCompact = false;

    /**
     * Creates an empty multiset. Further function {@link #add} shall be used.
     */
    public HashMultiset() {
    }

    /**
     * Creates a multiset from another one. The contents is copied using
     * {@link Collection.putAll()}
     *
     * @param m the multiset to be copied.
     */
    public HashMultiset(HashMultiset<T> m) {
        contents.putAll(m.contents);
    }

    /**
     * Creates a multiset containing one symbol.
     *
     * @param s the symbol to be included in the multiset.
     */
    public HashMultiset(T s) {
        contents.put(s, 1);
    }

    /**
     * Adds a symbol with a multiplicity to the multiset.
     *
     * @param s the symbol to be added.
     * @param num its multiplicity.
     * @return returns <code>this</code> for easier use.
     */
    public HashMultiset<T> add(T s, int num) {
        if (!contents.containsKey(s)) {
            contents.put(s, num);
        } else {
            contents.put(s, contents.get(s) + 1);
        }
        return this;
    }

    /**
     * Adds one symbol to the multiset.
     *
     * @param s the symbol to be added.
     * @return returns <code>this</code> for easier use.
     */
    public HashMultiset<T> add(T s) {
        return add(s, 1);
    }

    /**
     * Removes a symbol with a multiplicity from the multiset.
     *
     * @param s the symbol to be removed.
     * @param num its multiplicity. If greater than the actual multiplicity then
     * the multiplicity of the symbol becomes zero.
     * @return returns <code>this</code> for easier use.
     */
    public HashMultiset<T> remove(T s, int num) {
        if (contents.containsKey(s)) {
            int count = contents.get(s);
            if (count > 0) {
                if (count != num) {
                    contents.put(s, count > num ? count - num : 0);
                } else {
                    contents.remove(s); // maybe use should compact
                }
            }
        }
        return this;
    }

    /**
     * Removes one occurrence of the symbol from the multiset.
     *
     * @param s the symbol to be removed. If the multiset does not contain
     * <code>s</code> it remains unchanged.
     * @return returns <code>this</code> for easier use.
     */
    public HashMultiset<T> remove(T s) {
        return remove(s, 1);
    }

    /**
     * Returns the multiplicity of a symbol in the multiset. If the symbol is
     * not present then zero is returned.
     *
     * @param s the symbol.
     * @return the multiplicity of <code>s</code>.
     */
    public int value(T s) {
        if (!contents.containsKey(s)) {
            return 0;
        } else {
            return contents.get(s);
        }
    }

    /**
     * Adds a multiset to the current one.
     *
     * @param m the multiset to be added.
     * @return returns <code>this</code> for easier use.
     */
    public HashMultiset<T> sum(HashMultiset<T> m) {
        for (Entry<T, Integer> e : m) {
            if (contents.containsKey(e.getKey())) {
                contents.put(e.getKey(), contents.get(e.getKey()) + e.getValue());
            } else {
                contents.put(e.getKey(), e.getValue());
            }
        }
        return this;
    }

    /**
     * Subtracts a multiset from the current one. The subtraction is positive
     * (negative values are replaced by zero).
     *
     * @param m the multiset to be subtracted.
     * @return returns <code>this</code> for easier use.
     */
    public HashMultiset<T> difference(HashMultiset<T> m) {
        for (Entry<T, Integer> e : m) {
            if (contents.containsKey(e.getKey())) {
                int val = contents.get(e.getKey()) - e.getValue();
                val = val > 0 ? val : 0;
                if (val == 0) {
                    shouldCompact = true;
                    contents.remove(e.getKey()); // do not use compact
                } else {
                    contents.put(e.getKey(), val);
                }
            }
        }
        return this;
    }

    /**
     * Divides this multiset by the other one.
     *
     * @param m the multiset to divide.
     * @return returns the number of times <code>m</code> is included in the
     * current multiset.
     */
    public int divide(HashMultiset<T> m) {
        int val = Integer.MAX_VALUE;
        for (Entry<T, Integer> e : m) {
            if (contents.containsKey(e.getKey())) {
                val = Math.min(val, contents.get(e.getKey()) / e.getValue());
                //    contents.put(e.getKey(), val);
            } else if (e.getValue() > 0) {
                return 0;
            }
        }
        return val;
    }

    /**
     * Checks the inclusion of a multiset. If marked symbols are present then
     * they are not considered for this check.
     *
     * @param m the multiset to be checked.
     * @return true if <code>m</code> is included in this multiset.
     */
    public boolean includes(HashMultiset<T> m) {
        for (Entry<T, Integer> e : m) {
            T key = e.getKey();
            int val = e.getValue();
            if (val > 0 && (!contents.containsKey(key) || contents.get(key) < val)
                    || (markedcontents != null && markedcontents.containsKey(key)
                    && contents.get(key) < val + markedcontents.get(key))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the size of the multiset. The size of a multiset is defined as
     * the sum of multiplicities of all symbols.
     *
     * @return returns the size of the multiset.
     */
    public int size() {
        int size = 0;
        for (int v : contents.values()) {
            size = size + v;
        }
        return size;
    }

    @Override
    public Iterator<Entry<T, Integer>> iterator() {
        return contents.entrySet().iterator();
    }

    /**
     * Compacts the multiset by erasing entries with the multiplicity equal to
     * zero.
     */
    public void compact() {
        if (shouldCompact) {
//            for (T s : contents.keySet()) {
//                if (contents.get(s) == 0) {
//                    contents.remove(s);
//                }
//            }
            contents.entrySet().removeIf(e -> e.getValue() == 0);
            shouldCompact = false;
        }
    }

    /**
     * Marks a symbol with a multiplicity of the multiset.
     *
     * @param s the symbol to be marked.
     * @param num the multiplicity of <code>s</code>. If greater than the actual
     * multiplicity, then it is truncated to the actual value.
     * @return returns <code>this</code> for an easier use.
     */
    public HashMultiset<T> mark(T s, int num) {
        Integer val = contents.get(s);
        if (val != null && val > 0) {
            if (markedcontents == null) {
                markedcontents = new HashMap<T, Integer>();
            }

            int trunc = val >= num ? num : val;

            if (!markedcontents.containsKey(s)) {
                markedcontents.put(s, trunc);
            } else {
                int newval = trunc + markedcontents.get(s);
                if (newval <= val) {
                    markedcontents.put(s, newval);
                }
            }
        }

        return this;
    }

    /**
     * Marks a symbol of the multiset.
     *
     * @param s the symbol to be marked. If it is not present in the multiset,
     * then no action is performed.
     * @return returns <code>this</code> for an easier use.
     */
    public HashMultiset<T> mark(T s) {
        return mark(s, 1);
    }

    /**
     * Unmarks a symbol with a multiplicity of the multiset.
     *
     * @param s the symbol to be unmarked.
     * @param num the multiplicity of <code>s</code>.
     * @return returns <code>this</code> for an easier use.
     */
    public HashMultiset<T> unmark(T s, int num) {
        if (markedcontents.containsKey(s)) {
            int count = markedcontents.get(s);
            if (count > 0) {
                markedcontents.put(s, count > num ? count - num : 0);
            }
        }
        return this;
    }

    /**
     * Unmarks a symbol of the multiset.
     *
     * @param s the symbol to be unmarked.
     * @return returns <code>this</code> for an easier use.
     */
    public HashMultiset<T> unmark(T s) {
        return unmark(s, 1);
    }

    /**
     * Clears all marked symbols.
     */
    public void clearMark() {
        markedcontents = null;
    }

    /**
     * A {@link Set} view of objects from the multiset.
     *
     * @return returns the set of objects of the multiset.
     */
    public Set<T> objects() {
        compact();
        return contents.keySet();
    }

    /**
     * The string representation of a multiset. The elements are separated by
     * spaces and if the multiplicity is greater than one square brackets are
     * used: [a,2] signifies that the multiplicity of a is 2. If
     * {@link #expandMultiset} is equal to true then the notation [a,2] is
     * expanded to a a.
     *
     * @return the string representation of a multiset. The symbols are output
     * in the natural order
     */
    @Override
    public String toString() {
        boolean firstE = true;
        String result = "";
        for (Entry<T, Integer> e : contents.entrySet()
                .stream()
                .sorted(
                        // Sort by the string representation of each key
                        (a, b) -> a.getKey().toString().compareTo(b.getKey().toString())
                )
                .toList()) {
            if (e.getValue() > 0) {
                if (firstE) {
                    result = "";
                    firstE = false;
                } else {
                    result = result + " ";
                }
                String s = "";
                if (e.getValue() > 1) {
                    if (!expandMultiset) {
                        s = "[" + e.getKey().toString() + "," + e.getValue() + "]";
                    } else {
                        boolean fx = true;
                        String symb = e.getKey().toString();
                        for (int i = 0; i < e.getValue(); i++) {
                            if (fx) {
                                s = s + symb;
                                fx = false;
                            } else {
                                s = s + " " + symb;
                            }
                        }
                    }
                } else {
                    s = e.getKey().toString();
                }
                result = result + s;
            }
        }
        return result;
    }

    /**
     * Replaces the contents by the contents of another multiset.
     *
     * @param m the multiset to be wrapped.
     */
    protected void doWrap(HashMultiset<T> m) {
        this.contents = m.contents;
    }

      @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HashMultiset)) {
            return false;
        }

        var m = ((HashMultiset) obj);

        return m.includes(this) && this.includes(m);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.contents);
        return hash;
    }
}
