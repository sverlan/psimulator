/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author ver
 */
public class RandomRuleChoicer implements RuleChoicer{

    Random r = new Random();
    
    @Override
    public HashMultiset<Rule> choose(Set<HashMultiset<Rule>> ruleset) {
        if (ruleset.size()==0) return null;
        int choice = r.nextInt(ruleset.size())+1;
        Iterator<HashMultiset<Rule>> x = ruleset.iterator();
        for(int i=1;i<choice;i++) x.next();  // ! Starting from 1
        return x.next();
    }
    
}
