/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.rule;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author ver
 */
public class RuleSet extends HashMultiset<Rule>{
    
    ArrayList<Rule> order = new ArrayList<Rule>();

    
    @Override
    public RuleSet add(Rule r){
        super.add(r);
        order.add(r);
        return this;
    }
    
    public boolean applicable(Configuration conf){
        
        Rule superR = new Rule();
        
        for(Entry<Rule,Integer> e: this){
            Rule r = e.getKey();
            assert(e.getValue()==0);
            if(!r.checkPermitting(conf)||!r.checkForbidding(conf)) return false;
            for(int i=0;i<e.getValue();i++) superR.sumUList(r.U);
        }
        
        return superR.applicable(conf);
  }

    
    
    public Rule get(int i){
        Iterator<Rule> it = this.objects().iterator();
        Rule r=null;
       for(int j=0;j<=i;j++) 
           if(it.hasNext()) r=it.next();
       return r;
    }
    
    public void apply(Configuration conf){
        for(Entry<Rule,Integer> e: this){
            for(int i=0;i<e.getValue();i++) e.getKey().apply(conf);
        }
    }

       @Override
    public String toString() {
        boolean firstE = true;
        String result = "";
        for (Rule r: order) {
            int card = value(r);
            if (card > 0) {
                if (firstE) {
                    result = "";
                    firstE = false;
                } else {
                    result = result + " ";
                }
                String s = "";
                if (card > 1) {
                    if (!expandMultiset) {
                        s = "[" + r.toString() + "," + card + "]";
                    } else {
                        boolean fx = true;
                        String symb = r.toString();
                        for (int i = 0; i < card; i++) {
                            if (fx) {
                                s = s + symb;
                                fx = false;
                            } else {
                                s = s + " " + symb;
                            }
                        }
                    }
                } else {
                    s = r.toString();
                }
                result = result + s;
            }
        }
        return result;
    }
 
    
    
    
    
  public static RuleSet wrap(HashMultiset<Rule> mr){
      RuleSet rs = new RuleSet();
      rs.doWrap(mr);
      return rs;
  }  
    

  
}
