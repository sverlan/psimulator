package datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * This class permits to implement a configuration. 
 * <p>
 * A configuration consists of a list of cells (each having a unique id) 
 * 
 * @author Sergey Verlan
 * @version     1.0
 */
public class Configuration implements Iterable<Cell> {

    private LinkedHashMap<Integer, Cell> cells = new LinkedHashMap<Integer, Cell>();
    
    private HashMap<Label,List<Cell>> labelmap = new HashMap<Label,List<Cell>>();

    /**
     * Creates an empty configuration. Further function {@link #add} or 
     * {@link addAll} shall be used.
     * 
     */
    public Configuration() {
    }
    
    // Copy constructor
    
    public Configuration(Configuration that){
        for(var cell: that.cells.values()) {
            this.cells.put(cell.getID(), new Cell(cell));
        }
    }

    @Override
    public Iterator<Cell> iterator() {
        return cells.values().iterator();
    }
    
   /**
     * Adds one cell to the configuration.
     * 
     * @param c the cell to be added.
     */
    public void add(Cell c) {
        cells.put(c.getID(), c);
    }
    
    /**
     * Adds all cells from conf to the configuration.
     * 
     * @param conf the cells to be added.
     */
    public void addAll(Iterable<Cell> conf){
        for(Cell c: conf){
            cells.put(c.getID(), c);
        }
    }

     /**
     * Returns one particular cell from the configuration. If the cell is 
     * not present then <code>null</code> is returned. 
     * @param id the id of the cell.
     * @return the cell having the id <code>id</code>.
     */
    public Cell get(int id) {
        return cells.get(id);
    }

     /**
     * Checks the inclusion of a configuration. 
     * @param conf the configuration to be checked. 
     * @return true if <code>conf</code> is included in this configuration.
     */
    public boolean includes(Iterable<Cell> conf) {
        for (Cell c : conf) {
            int id = c.getID();
            if (!cells.containsKey(id) && c.getContents().size() > 0) {
                return false;
            }
            if (!cells.get(id).getContents().includes(c.getContents())) {
                return false;
            }
        }
        return true;

    }

    /**
     * Adds a configuration to the current one. The cells having same id
     * sum corresponding multisets.
     * @param conf the configuration to be added.
     */
    public void sum(Iterable<Cell> conf) {
        for (Cell c : conf) {
            int id = c.getID();
            if (!cells.containsKey(id)) {
                cells.put(id, c);
            } else {
                cells.get(id).getContents().sum(c.getContents());
            }
        }

    }

    /**
     * Subtracts a configuration from the current one. The cells having same id
     * subtract corresponding multisets. The subtraction is positive
     * (negative values are replaced by zero).
     * @param conf the configuration to be added.
     */
    public void difference(Iterable<Cell> conf) {
        for (Cell c : conf) {
            if (cells.containsKey(c.getID())) {
                cells.get(c.getID()).getContents()
                        .difference(c.getContents())
                        .compact();
                
            }
        }
    }

    /**
     * Divides this configuration by the other one.
     * @param conf the configuration to divide.
     * @return returns the number of times <code>conf</code> is included in the current 
     * configuration.
     */    
    public int divide(Iterable<Cell> conf) {
        int val = Integer.MAX_VALUE;
        for (Cell c : conf) {
            int id = c.getID();
            if (!cells.containsKey(id) && c.getContents().size() > 0) {
                return 0;
            }
            val = Math.min(val, cells.get(id).getContents().divide(c.getContents()));
        }
        return val;

    }

     
    public void constructLabelMap(){
        for(Cell c: cells.values()) {
            if(!labelmap.containsKey(c.getLabel())) labelmap.put(c.getLabel(), new ArrayList<Cell>());
            labelmap.get(c.getLabel()).add(c);
        }
    } 
     
    /**
     * The string representation of a configuration.
     * 
     * @return the string representation of a configuration.
     */    @Override
    public String toString() {
        return listToString(cells.values());
    }

    /**
     * Helper method that constructs a string representation of an Iterable
      * collection of cells (a configuration being a particular example).
     * 
      * @param conf the collection to be processed
      * @return the string representation of an iterable collection of cells.
     */
     public static String listToString(Iterable<Cell> conf) {
        String result = "";
        for (Cell c : conf) {
            result = result + c.toString();
        }
        return result;
    }
     
     @Override
     public boolean equals(Object obj){
         if (! (obj instanceof Configuration)){
             return false;
         }
         var conf = (Configuration) obj;
         return conf.includes(this) && this.includes(conf);
     }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cells);
        return hash;
    }
}
