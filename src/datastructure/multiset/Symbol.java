package datastructure.multiset;

import java.util.Objects;

/**
 * This class provides a simple symbol definition.
 *
 * <p>
 * A symbol is an entity that has a name.
 *
 * @author Sergey Verlan
 * @version 1.0
 */
public class Symbol implements Comparable<Symbol>{

    private final String name;

    /**
     * Creates a new instance of a symbol.
     *
     * @param name the name of the symbol.
     */
    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Symbol)
                && ((Symbol) obj).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public int compareTo(Symbol o) {
        return name.compareTo(o.name);
    }
    
    

}
