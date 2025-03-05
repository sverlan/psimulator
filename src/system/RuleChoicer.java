/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import java.util.Set;

/**
 *
 * @author ver
 */
public interface RuleChoicer {
    
    HashMultiset<Rule> choose(Set<HashMultiset<Rule>> ruleset);
    
}
