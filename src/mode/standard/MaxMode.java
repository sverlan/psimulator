/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mode.standard;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import java.util.Set;
import mode.DerivationMode;
import mode.basic.AsynchronousMode;
import mode.filter.MaxFilter;

/**
 *
 * @author ver
 */
public class MaxMode extends DerivationMode {

    public static final String publicName = "Maximal parallel";
    
    public MaxMode(){
        super(publicName);
    }
    

    @Override
    public Set<HashMultiset<Rule>> computeMultiset(Configuration conf, RuleSet rs) {
        DerivationMode max = new MaxFilter(new AsynchronousMode());
        return max.computeMultiset(conf, rs);
    }
}
