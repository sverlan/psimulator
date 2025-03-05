package datastructure.gtransducer;

import datastructure.Relation;
import datastructure.Relation.Pair;
import java.util.Iterator;

/**
 *
 * @author Sergey Verlan
 * @version 1.0
 */
public class SimpleGraphTransducer {
    Relation r;
    
    public SimpleGraphTransducer(Relation r){
        this.r = r;
    }
    
    public void delete(int x){
        Pair p=null;
        for(Iterator<Pair> ip = r.iterator(); ip.hasNext(); p = ip.next())
            if(p.getX()==x || p.getY() == x) ip.remove();
    }
    
    public void insert(int x){
        r.add(x, 0);
    }
    
    public void insert_edge(int x,int y){
        r.add(x, y);
    }
    
    public void delete_edge(int x, int y){
        r.remove(x, y);
    }
}
