/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psimulator.output;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import java.io.PrintStream;
import system.PSystem;

/**
 *
 * @author ver
 */
public class PSimulatorOutputText implements PSimulatorOutput {

    PrintStream out;

    public PSimulatorOutputText(PrintStream out) {
        this.out = out;
    }

    @Override
    public void generateHeader(PSystem p, int steps) {
        out.println("Initial configuration: ");
        out.println(p.getConf());
        out.println("Rules: ");
        out.println(p.getRules());
        out.println("Using maximally parallel derivation mode");
        out.println("Steps: " + steps);
        out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    @Override
    public void generateFooter() {
        // empty
    }

    @Override
    public void startStep(int stepno) {
        out.println("=====================================================");
        out.println("Step " + stepno);
    }

    @Override
    public void endStep(int stepno) {
        // empty
    }

    @Override
    public void startConfiguration(Configuration conf) {
        out.println("------------------------------------------------------");
        out.println("Current configuration:");
        out.println(conf);
        out.println("------------------------------------------------------");
    }

    @Override
    public void endConfiguration() {
        // empty
    }

    @Override
    public void startResult(HashMultiset<Rule> rules, Configuration result) {
        out.println("Choosed rule set: ");
        out.println(rules);

        out.println("New configuration: ");
        out.println(result);
        out.println(".......................................................");
        out.println();
    }

    @Override
    public void endResult() {
        // empty
    }

}
