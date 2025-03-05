/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;

import datastructure.Cell;
import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.multiset.Symbol;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.PSystem;

/**
 *
 * @author ver
 */
public class Pval implements Cloneable{
   public int i;

   public String s;
   
   public HashMultiset<Symbol> ms;
   public RuleSet rs;
   
   public PSystem ps;

   public Pair en;
   
   public Cell cl;
   
   public Configuration conf;

   public Rule rule;
   

   public Collection<Configuration> sconf;

    public Pval(Collection<Configuration> sconf) {
        this.sconf = sconf;
    }
   
   
   
    public Pval(Rule rule) {
        this.rule = rule;
    }
   
    public Pval(Cell cl) {
        this.cl = cl;
    }

    public Pval(Configuration conf) {
        this.conf = conf;
    }
   
   
   

    public Pval(Pair en) {
        this.en = en;
    }
   
    public Pval(PSystem ps) {
        this.ps = ps;
    }

    public Pval(RuleSet rs) {
        this.rs = rs;
    }

    public Pval(HashMultiset<Symbol> ms) {
        this.ms = ms;
    }

    @Override
   public Pval clone(){
        try {
            return (Pval) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Pval.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
   }

   public Pval(){
       
   }

   public Pval(int i){
       this.i = i;
   }


   public Pval(String s){
       this.s=s;
   }


}
