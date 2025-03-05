package datastructure;

import java.util.Iterator;

/**
 *
 * @author ver
 */
public class Instance implements Iterable<Integer>, Iterator<Integer>{

    private int[] data;
    private int it = -1;
    
    public Instance(int size){
        data = new int[size];
    }
    
    
    public int get(int i){
        assert i>0 && i<=data.length;
        return data[i-1];
    }
    
    public void put(int i, int val){
        assert i>0 && i<=data.length;
        data[i-1] = val;
    }
    
    @Override
    public boolean hasNext() {
        return it<data.length;
    }

    @Override
    public Integer next() {
        return data[++it];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Integer> iterator() {
        it = 0;
        return this;
    }
    
}
