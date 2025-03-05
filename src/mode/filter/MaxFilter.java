/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mode.filter;

import mode.FilterDerivationMode;
import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import java.util.Set;
import mode.DerivationMode;

/**
 *
 * @author ver
 */
public class MaxFilter extends FilterDerivationMode{

    public static final String publicName = "Maximality";

    
    public MaxFilter(DerivationMode m){
        super(m,publicName);
    }

    @Override
    public Set<HashMultiset<Rule>> computeMultiset(Configuration conf, RuleSet rs) {
        Set<HashMultiset<Rule>> appl = base.computeMultiset(conf, rs);
        HashMultiset<Rule> x = null;
        do {
            if (x != null) {
                appl.remove(x);
                x = null;
            }
            z:
            for (HashMultiset<Rule> m1 : appl) {
                for (HashMultiset<Rule> m2 : appl) {
                    if (m1 != m2) {
                        if (m1.includes(m2)) {
                            x = m2;
                            break z;
                        }
                    }
                }
            }
        } while (x != null);
        return appl;
    }
    
}
