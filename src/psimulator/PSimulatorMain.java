/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psimulator;

import datastructure.Configuration;
import datastructure.rule.RuleSet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import mode.standard.MaxMode;
import parser.Parser;
import psimulator.output.PSimulatorOutput;
import psimulator.output.PSimulatorOutputDot;
import psimulator.output.PSimulatorOutputText;
import system.PSystem;

/**
 *
 * @author ver
 */
public class PSimulatorMain {

    /**
     * @param args the command line arguments
     */
    public static void main2(String[] args) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

        if (args.length == 0) {
            System.out.println("Usage: name in_file");
        } else if (args.length == 1) {
            System.out.println("Parsing input file " + args[0]);
            Parser pp = new Parser(new FileReader(args[0]));
            pp.run();
            PSystem p = pp.getValue().ps;
            System.out.println("Configuration: ");
            System.out.println(p.getConf());
            System.out.println("Rules: ");
            System.out.println(p.getRules());
            p.setDm(new MaxMode());
            System.out.println("Using maximally parallel derivation mode");
            System.out.println("-----------------------------------------");

            //p.step();
            // Replacing the step to do all cases
            var rulesMS = p.getDm()
                    .computeMultiset(p.getConf(), p.getRules());
            for (var rules : rulesMS) {
                var conf = new Configuration(p.getConf());
                if (rules.size() > 0) {
                    RuleSet.wrap(rules).apply(conf);
                }
                System.out.println("Choosed rule set: ");
                System.out.println(rules);

                System.out.println("New configuration: ");
                System.out.println(conf);
                System.out.println("-----------------------------------------");
                System.out.println();
            }

        } else {
            System.out.println("Parsing input file " + args[0]);
            Parser pp = new Parser(new FileReader(args[0]));
            pp.run();
            PSystem p = pp.getValue().ps;
            System.out.println("Initial configuration: ");
            System.out.println(p.getConf());
            System.out.println("Rules: ");
            System.out.println(p.getRules());
            p.setDm(new MaxMode());
            System.out.println("Using maximally parallel derivation mode");
            var steps = Integer.parseInt(args[1]);
            System.out.println("Steps: " + steps);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            //p.step();
            // Replacing the step to do all cases
            var confList = new HashSet<Configuration>();
            confList.add(p.getConf());
            HashSet<Configuration> nextConfList = confList;
            for (int i = 0; i < steps; i++) {
                System.out.println("=====================================================");
                System.out.println("Step " + (i + 1));
                nextConfList = new HashSet<>();
                for (var conf : confList) {
                    System.out.println("------------------------------------------------------");
                    System.out.println("Current configuration:");
                    System.out.println(conf);
                    System.out.println("------------------------------------------------------");
                    var rulesMS = p.getDm()
                            .computeMultiset(conf, p.getRules());
                    for (var rules : rulesMS) {
                        var conf_new = new Configuration(conf);
                        if (rules.size() > 0) {
                            RuleSet.wrap(rules).apply(conf_new);
                        }
                        System.out.println("Choosed rule set: ");
                        System.out.println(rules);

                        System.out.println("New configuration: ");
                        System.out.println(conf_new);
                        System.out.println(".......................................................");
                        System.out.println();
                        nextConfList.add(conf_new);
                    }
                }
                confList = nextConfList;
            }

        }

    }

    public static void main(String[] args) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

        if (args.length == 0) {
            System.out.println("Usage: name in_file [steps] [dot]");
            return;
        }

        var steps = 1;

        var dot = false;

        if (args.length > 1) {
            steps = Integer.parseInt(args[1]);
        }

        if (args.length > 2) {
            dot = true;
        }
        if (!dot) {
            System.out.println("Parsing input file " + args[0]);
        }
        Parser pp = new Parser(new FileReader(args[0]));
        pp.run();
        PSystem p = pp.getValue().ps;
        var dm = new MaxMode();
        p.setDm(dm);

        HashSet<Configuration> history = new HashSet<>();
        history.add(p.getConf());

        PSimulatorOutput output;

        if (!dot) {
            output = new PSimulatorOutputText(System.out);
        } else {
            output = new PSimulatorOutputDot(System.out);
        }

        output.generateHeader(p, steps);

        // Replacing the step to do all cases
        var confList = new HashSet<Configuration>();
        confList.add(p.getConf());
        HashSet<Configuration> nextConfList = confList;
        for (int i = 0; i < steps; i++) {
            output.startStep(i + 1);
            nextConfList = new HashSet<>();
            for (var conf : confList) {
                output.startConfiguration(conf);
                var rulesMS = p.getDm()
                        .computeMultiset(conf, p.getRules());
                for (var rules : rulesMS) {
                    var conf_new = new Configuration(conf);
                    if (rules.size() > 0) {
                        RuleSet.wrap(rules).apply(conf_new);
                    }
                    output.startResult(rules, conf_new);

                    // check the history
                    if (!history.contains(conf_new)) {
                        nextConfList.add(conf_new);
                    }

                    output.endResult();
                }
                output.endConfiguration();
            }
            confList = nextConfList;
            output.endStep(i + 1);
            for (var c : nextConfList){
                history.add(c);
            }
        }

        output.generateFooter();

    }

}
