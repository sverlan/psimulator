/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psimulator.output;

import datastructure.Configuration;
import datastructure.multiset.HashMultiset;
import datastructure.rule.Rule;
import system.PSystem;

/**
 *
 * @author ver
 */
public interface PSimulatorOutput {

    /**
     *
     * Called at the beginning of the output
     * 
     * @param p
     * @param steps
     */
    public void generateHeader(PSystem p, int steps);

    /**
     * Called at the end of the output
     * 
     */
    public void generateFooter();

    /**
     *
     * Called at the beginning of each computational step
     * 
     * @param stepno
     */
    public void startStep(int stepno);

    /**
     *
     * Called at the end of each computational step
     * 
     * @param stepno
     */
    public void endStep(int stepno);

    /**
     *
     * For each step called at the beginning of the configuration description
     * 
     * @param conf
     */
    public void startConfiguration(Configuration conf);

    /**
     *
     * For each step called at the end of the configuration description
     * 
     */
    public void endConfiguration();

    /**
     *
     * For each configuration called at the beginning of each result 
     * 
     * @param rules
     * @param result
     */
    public void startResult(HashMultiset<Rule> rules, Configuration result);

    /**
     *
     * For each configuration called at the end of each result
     * 
     */
    public void endResult();
}
