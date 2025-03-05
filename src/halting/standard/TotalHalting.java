package halting.standard;

import datastructure.Configuration;
import datastructure.rule.RuleSet;
import halting.HaltingCondition;
import java.util.Collection;

/**
 * Standard total halting class.
 * <p>
 * The total halting returns true if no rule is applicable anymore for 
 * the current configuration.
 * 
 * @author Sergey Verlan
 * @version 1.0
 */
public class TotalHalting extends HaltingCondition{

    /**
     * Public name for automatic name retrieval.
     */
    public static final String publicName = "Total halting";
    
    /**
     * The default constructor.
     */
    public TotalHalting(){
        super(publicName);
    }
    
    @Override
    public boolean halt(Configuration conf, Collection<RuleSet> srules) {
        return srules.isEmpty();
    }
    
}
