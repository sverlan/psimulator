package datastructure.rule;

import datastructure.Cell;
import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.multiset.Symbol;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author ver
 */
public class Rule {

    Configuration U = new Configuration();
    Configuration V = new Configuration();
    HashSet<Configuration> P = new HashSet<Configuration>();
    HashSet<Configuration> F = new HashSet<Configuration>();

    public Rule() {
    }

    public Rule(Rule r) {
        this.U.addAll(r.U);
        this.V.addAll(r.V);
        // todo: deep copy 
        this.P.addAll(r.P);
        this.F.addAll(r.F);
    }

    public Rule(Configuration U, Configuration V, Collection<Configuration> P, Collection<Configuration> F) {
        this.U = U;
        this.V = V;
        this.P.addAll(P);
        this.F.addAll(F);
    }

    public void addUList(Cell c) {
        U.add(c);
    }

    public void addUList(List<Cell> c) {
        U.addAll(c);
    }

    public void addUList(Configuration c) {
        U.addAll(c);
    }

    public void sumUList(Configuration conf) {
        for (Cell c : conf) {
            int id = c.getID();
            boolean found = false;
            for (Cell c1 : U) {
                if (c1.getID() == id) {
                    c1.getContents().sum(c.getContents());
                    found = true;
                    break;
                }
            }
            if (!found) {
                HashMultiset<Symbol> t = new HashMultiset<Symbol>(c.getContents());
                //    for(Entry<Symbol,Integer> e: c.getContents()) t.add(e.getKey(),e.getValue());
                U.add(new Cell(c.getID(), t));
            } //copy of c
        }

    }

    public void addVList(Cell c) {
        V.add(c);
    }

    public void addVList(List<Cell> c) {
        V.addAll(c);
    }

    public void addVList(Configuration c) {
        V.addAll(c);
    }

    public void addPList(Cell c) {
        Configuration a = new Configuration();
        a.add(c);
        P.add(a);
    }

    public void addPList(Configuration cl) {
        P.add(cl);
    }

    public void addPList(Collection<Configuration> cl) {
        P.addAll(cl);
    }

    public void addFList(Cell c) {
        Configuration a = new Configuration();
        a.add(c);
        F.add(a);
    }

    public void addFList(Configuration cl) {
        F.add(cl);
    }

    public void addFList(Collection<Configuration> cl) {
        F.addAll(cl);
    }

    public void addRuleData(Rule r) {
        addUList(r.U);
        addVList(r.V);
        addPList(r.P);
        addFList(r.F);
    }

    public boolean checkPermitting(Configuration conf) {
        for (Configuration ps : P) {
            for (Cell c : ps) {
                if (!conf.get(c.getID()).getContents().includes(c.getContents())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkForbidding(Configuration conf) {
        for (Configuration ps : F) {
            for (Cell c : ps) {
                if (conf.get(c.getID()).getContents().includes(c.getContents())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkLHS(Configuration conf) {
        return conf.includes(this.U);
    }

    public boolean applicable(Configuration conf) {
        // permitting
        if (!checkPermitting(conf)) {
            return false;
        }
        // forbidding
        if (!checkForbidding(conf)) {
            return false;
        }
        //U
        if (!checkLHS(conf)) {
            return false;
        }
        return true;
    }

    public int nbApp(Configuration conf) {
        return conf.divide(U);
    }

    public void apply(Configuration conf) {
        //U
        conf.difference(U);
        //V
        conf.sum(V);
    }

    @Override
    public String toString() {
        String result = "";

        result = Configuration.listToString(U);
        result = result + "->";
        result = result + Configuration.listToString(V);
        result = result + " ; {";
        boolean first = true;
        for (Configuration cs : P) {
            if (first) {
                result = result + cs.toString();
                first = false;
            } else {
                result = result + ", " + cs.toString();
            }
        }
        result = result + "} ; {";
        first = true;
        for (Configuration cs : F) {
            if (first) {
                result = result + cs.toString();
                first = false;
            } else {
                result = result + ", " + cs.toString();
            }

        }
        result = result + "}";
        return result;
    }
}
