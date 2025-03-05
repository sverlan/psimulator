package halting;

import datastructure.Configuration;
import datastructure.rule.RuleSet;
import java.util.Collection;

/**
 * This is the abstract class for the definition of the halting condition.
 * 
 * @author Sergey Verlan
 * @version 1.0
 */
public abstract class HaltingCondition {
    
    String name;

    /**
     * Default constructor.
     * 
     * @param name the name of the condition
     */
    public HaltingCondition(String name) {
        this.name = name;
    }
    
    /**
     * 
     * The checking function.
     * 
     * @param conf the current configuration
     * @param srules the set of rules
     * @return true if the halting condition is met on conf (with rules srules)
     */
    public abstract boolean halt(Configuration conf, Collection<RuleSet> srules);
    
    
    @Override
    public String toString(){
        return name;
    }
    
}
