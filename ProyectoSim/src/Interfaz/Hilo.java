
package Interfaz;

import Genetica.EvaluarFit;
import Genetica.SymbolicRegression;
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
    private EvaluarFit es = new EvaluarFit();

    public Hilo(SymbolicRegression SR, String[] args, Principal frame, int i){
        sr=SR;
        s=args;
        x=i;
        f=frame;
    }

    public void run() {          // sobrecarga el metodo run la clase Thread
        try {
            // sobrecarga el metodo run la clase Thread
            es.results = new Double[es.numCol];

            sr.ventana=f;
            //f.RenovarText("Presentation: " + sr.presentation);
            //System.out.println("Presentation: " + sr.presentation);
            if (es.variableSal == null)
              es.variableSal = es.varEntrada;
           
            if (sr.variableNames == null) {
              sr.variableNames = new String[es.varEntrada + 1];
              for (int i = 0; i < es.varEntrada + 1; i++) {
                sr.variableNames[i] = "V" + i;
              }
            }

            //La variable configuracion contiene los parametros con los que
            //se ejecuta el algoritmo genetico
            GPConfiguration configuracion = new GPConfiguration();
            // We use a delta fitness evaluator because we compute a defect rate, not
            // a point score!
            // ----------------------------------------------------------------------
            configuracion.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
            configuracion.setMaxInitDepth(sr.maxInitDepth);
            configuracion.setPopulationSize(sr.populationSize);
            // Default selectionMethod is is TournamentSelector(3)
            if (sr.tournamentSelectorSize > 0) {
              configuracion.setSelectionMethod(new TournamentSelector(sr.tournamentSelectorSize));
            }
            /**
             * The maximum depth of an individual resulting from crossover.
             */
            configuracion.setMaxCrossoverDepth(sr.maxCrossoverDepth);
            configuracion.setFitnessFunction(new EvaluarFit.EvaluarFitness());
            /**
             * @param a_strict true: throw an error during evolution in case a situation
             * is detected where no function or terminal of a required type is declared
             * in the GPConfiguration; false: don't throw an error but try a completely
             * different combination of functions and terminals
             */
            // config.setStrictProgramCreation(true);
            configuracion.setStrictProgramCreation(false);
            // Default from GPConfiguration.java

            /**
             * In crossover: If random number (0..1) < this value, then choose a function
             * otherwise a terminal.
             */
            configuracion.setFunctionProb(sr.functionProb);
            /**
             * The probability that a reproduction operation is chosen during evolution.
             * Must be between 0.0d and 1.0d. crossoverProb + reproductionProb must equal
             * 1.0d.
             */
            configuracion.setReproductionProb(sr.reproductionProb);
            /**
             * The probability that a node is mutated during growing a program.
             */
            configuracion.setMutationProb(sr.mutationProb);
            /**
             * The probability that the arity of a node is changed during growing a
             * program.
             */
            configuracion.setDynamizeArityProb(sr.dynamizeArityProb);
            /**
             * Percentage of the population that will be filled with new individuals
             * during evolution. Must be between 0.0d and 1.0d.
             */
            configuracion.setNewChromsPercent(sr.newChromsPercent);
            /**
             * The minimum depth of an individual when the world is created.
             */
            configuracion.setMinInitDepth(sr.minInitDepth);
            /**
             * If m_strictProgramCreation is false: Maximum number of tries to construct
             * a valid program.
             */
            configuracion.setProgramCreationMaxTries(sr.programCreationMaxTries);
            GPProblem problem = new SymbolicRegression(configuracion);
            
            sr.main(s, 0, f, problem);

        } catch (Exception ex) {
            System.out.print("no corre el hilo;");
        }
   }
}
