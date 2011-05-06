
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaz;

import Genetica.SymbolicRegression;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.TournamentSelector;

/**
 *
 * @author Diego
 */
public class Hilo extends Thread{

    public SymbolicRegression sr;
    public String[] s;
    public int x;
    public Principal f;

    public Hilo(SymbolicRegression SR, String[] args, Principal frame, int i){
        sr=SR;
        s=args;
        x=i;
        f=frame;
    }

    public void run() {          // sobrecarga el metodo run la clase Thread
        try {
            // sobrecarga el metodo run la clase Thread
            sr.results=new Double[sr.numRows];
            sr.ventana=f;
            //f.RenovarText("Presentation: " + sr.presentation);
            //System.out.println("Presentation: " + sr.presentation);
            if (sr.outputVariable == null) {
              sr.outputVariable = sr.numInputVariables;
            }
            if (sr.variableNames == null) {
              sr.variableNames = new String[sr.numInputVariables + 1];
              for (int i = 0; i < sr.numInputVariables + 1; i++) {
                sr.variableNames[i] = "V" + i;
              }
            }
            //f.RenovarText("\n"+"output_variable: " + sr.variableNames[sr.outputVariable] +
            //                   " (index: " + sr.outputVariable + ")");///
            //System.out.println("output_variable: " + sr.variableNames[sr.outputVariable] +
            //                   " (index: " + sr.outputVariable + ")");
            // Setup the algorithm's parameters.
            // ---------------------------------
            GPConfiguration config = new GPConfiguration();
            // We use a delta fitness evaluator because we compute a defect rate, not
            // a point score!
            // ----------------------------------------------------------------------
            config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
            config.setMaxInitDepth(sr.maxInitDepth);
            config.setPopulationSize(sr.populationSize);
            // Default selectionMethod is is TournamentSelector(3)
            if (sr.tournamentSelectorSize > 0) {
              config.setSelectionMethod(new TournamentSelector(sr.tournamentSelectorSize));
            }
            /**
             * The maximum depth of an individual resulting from crossover.
             */
            config.setMaxCrossoverDepth(sr.maxCrossoverDepth);
            config.setFitnessFunction(new SymbolicRegression.FormulaFitnessFunction());
            /**
             * @param a_strict true: throw an error during evolution in case a situation
             * is detected where no function or terminal of a required type is declared
             * in the GPConfiguration; false: don't throw an error but try a completely
             * different combination of functions and terminals
             */
            // config.setStrictProgramCreation(true);
            config.setStrictProgramCreation(false);
            // Default from GPConfiguration.java

            /**
             * In crossover: If random number (0..1) < this value, then choose a function
             * otherwise a terminal.
             */
            config.setFunctionProb(sr.functionProb);
            /**
             * The probability that a reproduction operation is chosen during evolution.
             * Must be between 0.0d and 1.0d. crossoverProb + reproductionProb must equal
             * 1.0d.
             */
            config.setReproductionProb(sr.reproductionProb);
            /**
             * The probability that a node is mutated during growing a program.
             */
            config.setMutationProb(sr.mutationProb);
            /**
             * The probability that the arity of a node is changed during growing a
             * program.
             */
            config.setDynamizeArityProb(sr.dynamizeArityProb);
            /**
             * Percentage of the population that will be filled with new individuals
             * during evolution. Must be between 0.0d and 1.0d.
             */
            config.setNewChromsPercent(sr.newChromsPercent);
            /**
             * The minimum depth of an individual when the world is created.
             */
            config.setMinInitDepth(sr.minInitDepth);
            /**
             * If m_strictProgramCreation is false: Maximum number of tries to construct
             * a valid program.
             */
            config.setProgramCreationMaxTries(sr.programCreationMaxTries);
            GPProblem problem = new SymbolicRegression(config);
            
            sr.main(s, 0, f, problem);

        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
