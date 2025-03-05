/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mode;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import java.util.Set;

/**
 *
 * @author ver
 */
public abstract class DerivationMode {
    
    final String name;
    
    public DerivationMode(String name){
        this.name = name;
    }
    
    public abstract Set<HashMultiset<Rule>> computeMultiset(Configuration conf, RuleSet rs);
    
    @Override
    public String toString(){
        return name;
    }
    
}
