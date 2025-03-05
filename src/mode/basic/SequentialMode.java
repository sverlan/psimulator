/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mode.basic;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import java.util.HashSet;
import java.util.Set;
import mode.DerivationMode;

/**
 *
 * @author ver
 */
public class SequentialMode extends DerivationMode {

    public static final String publicName = "Sequential";

    public SequentialMode() {
        super(publicName);
    }

    @Override
    public Set<HashMultiset<Rule>> computeMultiset(Configuration conf, RuleSet rs) {
        Set<HashMultiset<Rule>> res = new HashSet<HashMultiset<Rule>>();
        for (Rule r : rs.objects()) {
            if (r.applicable(conf)) {
                res.add(new HashMultiset<Rule>(r));
            }
        }
        return res;
    }
}
