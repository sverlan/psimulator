package datastructure;

import datastructure.Relation.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * A dumb implementation of a relation.
 * 
 * @author Sergey Verlan
 * @version 1.0
 */
public class Relation implements Iterable<Pair>{
    
    private List<Pair> data = new ArrayList<Pair>();
    
    public Relation(){
    }
    
    public void add(int x, int y){
        data.add(new Pair(x,y));
    }
    
    public void remove(int x, int y){
        data.remove(new Pair(x,y));
    }
    
    public boolean check(int x, int y){
        return data.contains(new Pair(x,y));
    }

    public boolean consistent(Relation r1){
        return data.containsAll(r1.data);
    }
    
    @Override
    public Iterator<Pair> iterator() {
        return data.iterator();
    }
    
    public class Pair{
        int x, y;
        
        public Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public int getX(){ return x; }
        
        public int getY() {return y; }

        @Override
        public boolean equals(Object obj) { 
           if (obj == null) return false;
           if (obj == this) return true;
           if (obj.getClass() != getClass()) return false;
           return ((Pair)obj).x == x && ((Pair)obj).y==y;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 47 * hash + this.x;
            hash = 47 * hash + this.y;
            return hash;
        }

    }
    
}
