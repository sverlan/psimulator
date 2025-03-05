/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mode.basic;

import mode.Enumeration;
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
public class AsynchronousModeX extends DerivationMode {
    
    public static final String publicName = "AsynchronousX";
    
    public AsynchronousModeX(){
        super(publicName);
    }

    @Override
    public Set<HashMultiset<Rule>> computeMultiset(Configuration conf, RuleSet rs) {
        Set<HashMultiset<Rule>> res = new HashSet<HashMultiset<Rule>>();
        int[] maxv = new int[rs.size()];
        for (int i = 0; i < rs.size(); i++) {
            maxv[i] = rs.get(i).nbApp(conf);
        }
        Enumeration e = new Enumeration(maxv);
        while (e.hasNext()) {
            int[] currv = e.next();
            RuleSet test = new RuleSet();
            for (int i = 0; i < currv.length; i++) {
                if (currv[i] != 0) {
                    test.add(rs.get(i),currv[i]);
                }
            }
            if (test.applicable(conf)) {
                HashMultiset<Rule> r1 = new HashMultiset<Rule>(test);
 //               for (Rule r : test.objects()) {
 //                   r1.add(r);
 //               }
                res.add(r1);
            }
        }


        return res;

    }
}
