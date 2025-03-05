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
import mode.FilterDerivationMode;
import mode.basic.AsynchronousMode;
import mode.filter.MaxFilter;

/**
 *
 * @author ver
 */
public class MaxFilterMode extends FilterDerivationMode{

    public MaxFilterMode(int i){
        super(new MaxFilter(new AsynchronousMode()),"");
    }
    
    @Override
    public Set<HashMultiset<Rule>> computeMultiset(Configuration conf, RuleSet rs) {
        return base.computeMultiset(conf, rs);
    }
    
}
