/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import datastructure.multiset.Symbol;
import java.util.HashMap;

/**
 *
 * @author ver
 */
public class SymbolTable {
    private HashMap<String,Symbol> table = new HashMap<String, Symbol>();
    
    public SymbolTable(){
        
    }
    
    public void put(Symbol s){
        table.put(s.toString(), s);
    }
    
    public Symbol put(String name){
        Symbol s = new Symbol(name);
        table.put(name, s);
        return s;
    }
    
    public boolean exists(String name){
        return table.containsKey(name);
    }
    
    public boolean exists(Symbol s){
        return table.containsValue(s);
    }
    
    public Symbol get(String name){
        if(!exists(name)) return put(name);
        else return table.get(name);
    }
}
