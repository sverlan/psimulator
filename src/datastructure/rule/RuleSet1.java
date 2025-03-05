/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.rule;

import datastructure.Configuration;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ver
 */
public class RuleSet1 implements Iterable<Rule>{
    private ArrayList<Rule> rules = new ArrayList<Rule>();
    
    public RuleSet1(){
        
    }
    
    public void add(Rule r){
        rules.add(r);
    }
    public void add(Rule r, int num){
        for (int i=0;i<num;i++) rules.add(r);
    }
    
    public boolean applicable(Configuration conf){
        
        Rule superR = new Rule();
        
        for(Rule r: rules){
            if(!r.checkPermitting(conf)||!r.checkForbidding(conf)) return false;
            superR.sumUList(r.U);
        }
        
        return superR.applicable(conf);
  }

    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }
    
    public int size(){
        return rules.size();
    }
    
    public Rule get(int i){
        return rules.get(i);
    }
    
}
