package datastructure;

import datastructure.multiset.HashMultiset;
import datastructure.multiset.Symbol;
import java.util.Objects;

/**
 * This class permits to implement a Cell.
 * <p>
 * A cell contains a multiset, a label and has a unique id.
 *
 *
 * @author Sergey Verlan
 * @version 1.0
 */
public class Cell {

    private int id;
    private Label label;
    private HashMultiset<Symbol> contents;

    private static Label emptyLabel = new Label("");

    /**
     * Returns the multiset being the contents of the cell.
     *
     * @return the contents multiset
     */
    public HashMultiset<Symbol> getContents() {
        return contents;
    }

    /**
     * Returns the id of the cell.
     *
     * @return the cell's id
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the label of the cell.
     *
     * @return the cell's label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Updates the label of the cell.
     *
     * @param label the new label
     */
    public void setLabel(Label label) {
        this.label = label;
    }

    /**
     * Creates a cell with the specified id, label and having the contents m.
     *
     * @param id the id of the cell
     * @param label the label of the cell
     * @param m the contents of the cell
     */
    public Cell(int id, Label label, HashMultiset<Symbol> m) {
        this.label = label;
        this.id = id;
        this.contents = m;
    }

    /**
     * Creates a cell with the specified id and having the contents m.
     *
     * @param id the id of the cell
     * @param m the contents of the cell
     */
    public Cell(int id, HashMultiset<Symbol> m) {
        this(id, emptyLabel, m);
    }

    /**
     * Creates an empty cell with the specified id.
     *
     * @param id the id of the cell
     */
    public Cell(int id) {
        this(id, new HashMultiset<Symbol>());
    }

    // Copy constructor
    public Cell(Cell that) {
        this.id = that.id;
        this.label = that.label;
        this.contents = new HashMultiset<>(that.contents);
    }

    /**
     * The string representation of a cell (as (id, contents) ). If the label is
     * not empty then it is also included in the representation.
     *
     * @return the string representation of a cell.
     */
    @Override
    public String toString() {
        if (label == emptyLabel) {
            return "(" + id + ", " + contents.toString() + ")";
        } else {
            return "(" + id + ", " + label + ", " + contents.toString() + ")";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell)) {
            return false;
        }
        var c1 = (Cell) obj;
        if (c1.getContents().size() != this.getContents().size()) {
            return false;
        }
        
        if (c1.getID() != this.getID()){
            return false;
        }
        
        return this.contents.includes(c1.getContents()) && 
                c1.getContents().includes(this.contents);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.contents);
        return hash;
    }

}
