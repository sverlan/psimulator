/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import mode.DerivationMode;

/**
 *
 * @author ver
 */
public class PSystem {

    Configuration conf;
    RuleSet rules;
    DerivationMode dm;
    RuleChoicer rc;
    HashMultiset<Rule> lastRules;

    public RuleChoicer getRc() {
        return rc;
    }

    public void setRc(RuleChoicer rc) {
        this.rc = rc;
    }

    public PSystem(Configuration conf, RuleSet rules, DerivationMode dm, RuleChoicer rc) {
        this.conf = conf;
        this.rules = rules;
        this.dm = dm;
        this.rc = rc;
    }

    public PSystem() {
    }

    public PSystem(Configuration conf, RuleSet rules) {
        this(conf, rules, null, new RandomRuleChoicer());
    }

    public PSystem(Configuration conf, RuleSet rules, DerivationMode dm) {
        this(conf, rules, dm, new RandomRuleChoicer());
    }

    public Configuration getConf() {
        return conf;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    public DerivationMode getDm() {
        return dm;
    }

    public void setDm(DerivationMode dm) {
        this.dm = dm;
    }

    public RuleSet getRules() {
        return rules;
    }

    public void setRules(RuleSet rules) {
        this.rules = rules;
    }

    public void step(int n) {
        for (int i = 0; i < n; i++) {
            lastRules = rc.choose(dm.computeMultiset(conf, rules));
            if (lastRules.size() > 0) {
                RuleSet.wrap(lastRules).apply(conf);
            }
        }
    }

    public void step() {
        step(1);
    }

    public HashMultiset<Rule> getLastRules() {
        return lastRules;
    }
}
