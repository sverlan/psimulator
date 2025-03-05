package halting.standard;

import datastructure.Configuration;
import datastructure.rule.RuleSet;
import halting.HaltingCondition;
import java.util.Collection;

/**
 * Standard signal halting class.
 * <p>
 * The signal halting returns true if the configuration contains
 * some particular subconfiguration (signal)
 * 
 * 
 * @author Sergey Verlan
 * @version 1.0
 */
public class SignalHalting extends HaltingCondition{

    /**
     * Public name for automatic name retrieval.
     */
    public static final String publicName= "Signal halting";
    
    Configuration signal;
    
    /**
     * Constructs an instance with the given signal.
     * 
     * @param signal the halting signal
     */
    public SignalHalting(Configuration signal){
        super(publicName);
        this.signal=signal;
    }
    
    /**
     * The default constructor. Will halt in any configuration.
     * 
     */
    public SignalHalting(){
        this(new Configuration());
    }

    /**
     * The getter for the signal.
     * 
     * @return the signal configuration
     */
    public Configuration getSignal() {
        return signal;
    }

    /**
     * 
     * Sets the signal configuration.
     * 
     * @param signal the new configuration
     */
    public void setSignal(Configuration signal) {
        this.signal = signal;
    }
    

    @Override
    public boolean halt(Configuration conf, Collection<RuleSet> srules) {
        return conf.includes(signal);
    }
    
}
