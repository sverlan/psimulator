/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psimulator;

import datastructure.Cell;
import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.multiset.Symbol;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import mode.basic.AsynchronousMode;
import mode.DerivationMode;
import mode.standard.MaxMode;
import mode.basic.SequentialMode;
import mode.standard.MaxSetMode;
import parser.Parser;
import system.PSystem;

/**
 *
 * @author ver
 */
public class PSimulatorTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

        // TODO code application logic here

        Symbol a = new Symbol("a");
        Symbol b = new Symbol("b");
        Symbol c = new Symbol("c");

        HashMultiset<Symbol> m1 = new HashMultiset<Symbol>();
        m1.add(a, 2).add(b, 3);

        m1.expandMultiset = true;

        HashMultiset m2 = new HashMultiset();
        m2.add(a, 2).add(b, 2).add(c);

        HashMultiset m3 = new HashMultiset();
        m3.add(b, 1).add(c, 2);

        Configuration cf = new Configuration();
        cf.add(new Cell(1, m1));
        cf.add(new Cell(2, m2));
        cf.add(new Cell(3, m3));
        
        HashMultiset m2_1 = new HashMultiset();
        m2_1.add(b, 2).add(a, 2).add(c);
        
        System.out.println("EQUALS1:"+(m2.equals(m2_1)));
        
        var m1_1 = new HashMultiset<>(m1);
        var m3_1 = new HashMultiset<>(m3);
     
        var cf1 = new Configuration();
        cf1.add(new Cell(1, m1_1));
        cf1.add(new Cell(2, m2_1));
        cf1.add(new Cell(3, m3_1));
        
           System.out.println("EQUALS2:"+(cf.equals(cf1)));

        Rule r1 = new Rule();

        HashMultiset k1 = new HashMultiset();
        k1.add(a).add(b);
        HashMultiset k2 = new HashMultiset();
        k2.add(a).add(c);

        r1.addUList(new Cell(1, k1));
        r1.addUList(new Cell(2, k2));

        System.out.println("k1,k2 applicable on m1,m2,m3: " + r1.applicable(cf));

        HashMultiset fc = new HashMultiset();
        fc.add(c);

        r1.addFList(new Cell(3, fc));
        System.out.println("k1,k2,fc applicable on m1,m2,m3: " + r1.applicable(cf));

        System.out.println("Configuration: " + cf);

        System.out.println("Rule 1: " + r1);
        //    System.out.println(m1.getType()+" "+m2.getType()+" "+m3.getType());

        Rule r2 = new Rule();

        HashMultiset k3 = new HashMultiset();
        k3.add(a, 2).add(b);
        HashMultiset k4 = new HashMultiset();
        k4.add(b).add(c);

        r2.addUList(new Cell(2, k3));
        r2.addUList(new Cell(3, k4));

        System.out.println("Rule 2: " + r2);

        HashMultiset k5 = new HashMultiset();
        k5.add(b, 1);
        Rule r3 = new Rule();

        r3.addUList(new Cell(2, k5));

        System.out.println("Rule 3: " + r3);

        RuleSet rs = new RuleSet();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);

        DerivationMode dm = new SequentialMode();

        Set<HashMultiset<Rule>> app = dm.computeMultiset(cf, rs);

        System.out.println("Applicable rules (seq): ");

        for (HashMultiset<Rule> mr : app) {
            System.out.println("{");
            System.out.println(mr);
            System.out.println("}");
        }

        dm = new AsynchronousMode();

        app = dm.computeMultiset(cf, rs);

        System.out.println("Applicable rules (set asyn): ");

        for (HashMultiset<Rule> mr : app) {
            System.out.println("{");
            System.out.println(mr);
            System.out.println("}");
        }
        dm = new MaxMode();

        app = dm.computeMultiset(cf, rs);
        
        System.out.println("Applicable rules (max): ");

        for (HashMultiset<Rule> mr : app) {
            System.out.println("{");
            System.out.println(mr);
            System.out.println("}");
        }
        dm = new MaxSetMode();

        app = dm.computeMultiset(cf, rs);

        System.out.println("Applicable rules (max set): ");

        for (HashMultiset<Rule> mr : app) {
            System.out.println("{");
            System.out.println(mr);
            System.out.println("}");
        }

        if(args.length>0){
            System.out.println("Parsing input file "+args[0]);
            Parser pp = new Parser(new FileReader(args[0]));
            pp.run();
            PSystem p = pp.getValue().ps;
            System.out.println("Configuration: ");
            System.out.println(p.getConf());
            System.out.println("Rules: ");
            System.out.println(p.getRules());
            p.setDm(new MaxMode());
            p.step();
            System.out.println("Choosed rule set: ");
            System.out.println(p.getLastRules());

            System.out.println("New configuration: ");
            System.out.println(p.getConf());
         
            
        }
        
 /*             
        for(Class cl: PackageList.getClassesInPackage("mode.standard")){
            System.out.print(cl.getName()+" : ");
            Object inst = cl.getConstructor().newInstance();
          System.out.println(inst.getClass().getMethod("toString").invoke(inst));
        }
  * 
  */
        
        for(String s: PSystemComponentsList.dmList()){
            System.out.println(s);
        }
        
        System.out.println("---------------");
        
        for(String s: PSystemComponentsList.haltingList()){
            System.out.println(s);
        }
        System.out.println("---------------");
        
        for(String dms: PSystemComponentsList.filterList()){
            System.out.println(dms.toString());
        }
        System.out.println("---------------");
        
        /*
        for(DerivationMode dms: PSystemComponentsList.list(dm,"mode")){
        System.out.println(dms.toString());
        }
         */
        
        DerivationMode y = PSystemComponentsList.filterInstance("Maximality",dm);
        System.out.println(y);
    }
    
    
    
}
