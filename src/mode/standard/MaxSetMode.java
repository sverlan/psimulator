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
import mode.basic.SetMode;
import mode.filter.MaxFilter;

/**
 *
 * @author ver
 */
public class MaxSetMode extends DerivationMode {

    public static final String publicName = "Max Set";

    
    public MaxSetMode(){
        super(publicName);
    }

    @Override
    public Set<HashMultiset<Rule>> computeMultiset(Configuration conf, RuleSet rs) {
        DerivationMode max = new MaxFilter(new SetMode());
        return max.computeMultiset(conf, rs);
    }
}
