/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psimulator.output;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import java.io.PrintStream;
import java.util.HashSet;
import system.PSystem;

/**
 *
 * @author ver
 */
public class PSimulatorOutputDot implements PSimulatorOutput {

    PrintStream out;

    // memorizes the configuration for the results
    Configuration currentConfiguration;

    // outputs the lists of all nodes at the end of the dot file
    HashSet<Configuration> configurationList = new HashSet<>();

    public PSimulatorOutputDot(PrintStream out) {
        this.out = out;
    }

    @Override
    public void generateHeader(PSystem p, int steps) {
        out.println("strict digraph {");
        configurationList.add(p.getConf());
    }

    @Override
    public void generateFooter() {
        out.println();
        out.println("// List of nodes for simpler postprocessing");
        out.println();
        for (var c : configurationList) {
            out.println("\"" + c + "\"");
        }
        out.println();
        out.println("}");
    }

    @Override
    public void startStep(int stepno) {
        // empty
    }

    @Override
    public void endStep(int stepno) {
        // empty
    }

    @Override
    public void startConfiguration(Configuration conf) {
        currentConfiguration = conf;
    }

    @Override
    public void endConfiguration() {
        // empty
    }

    @Override
    public void startResult(HashMultiset<Rule> rules, Configuration result) {
        out.print("\"" + currentConfiguration + "\" -> ");
        out.print("\"" + result + "\"");

        // out.println(rules);
        out.println();
        configurationList.add(result);
    }

    @Override
    public void endResult() {
        // empty
    }

}
