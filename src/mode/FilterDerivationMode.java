/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mode;

/**
 *
 * @author ver
 */
public abstract class FilterDerivationMode extends DerivationMode{
    
    protected DerivationMode base;
    
    public FilterDerivationMode(DerivationMode base, String name){
        super(name);
        this.base = base;
    }
    
    public DerivationMode getBase(){
        return this.base;
    }
    
    @Override
    public String toString(){
        return name+" filter on "+base;
    }
    
}
