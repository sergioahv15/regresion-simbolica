
package Interfaz;

import Genetica.EvaluarFit;
import Genetica.ProgramacionGenetica;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.TournamentSelector;

/**
 *
 * @author Diego Chapeton and Fabian Sanin
 */
public class Hilo extends Thread{

    public ProgramacionGenetica sr;
    public String[] s;
    public int x;
    public Principal f;
    private EvaluarFit es = new EvaluarFit();

    public Hilo(ProgramacionGenetica SR, String[] args, Principal frame, int i){
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
            es.variableSal = es.varEntrada;
            //La variable configuracion contiene los parametros con los que
            //se ejecuta el algoritmo genetico
            GPConfiguration configuracion = new GPConfiguration();
            // Se configura el Fitness
            configuracion.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
            configuracion.setMaxInitDepth(sr.maxProf);
            configuracion.setPopulationSize(sr.poblacion);

            if (sr.tamTorneo > 0) {
              configuracion.setSelectionMethod(new TournamentSelector(sr.tamTorneo));
            }
            /**
             * La profundidad maxima de un resultado de cruce.
             */
            configuracion.setMaxCrossoverDepth(sr.profCruce);
            configuracion.setFitnessFunction(new EvaluarFit.EvaluarFitness());
            
            configuracion.setStrictProgramCreation(false);

            /**
             * En un cruce: si el numero aleatorio (0..1) < este valor, entonces selleciona una funcion
             * de otro modo una terminal.
             */
            configuracion.setFunctionProb(sr.probFunc);
            /**
             * La probabilidad de que una operación de reproducción se eliga durante la evolución.
             * Debe estar entre 0.0d y 1.0d. proCruce + proReproduccion = 1.0d
             */
            configuracion.setReproductionProb(sr.probRepro);
            /**
             * La probabilidad de Mutuacion
             */
            configuracion.setMutationProb(sr.probMuta);
                  
            /**
             * Porcentaje de la población que estará lleno de nuevos individuos
             * durante la evolucion.
             */
            configuracion.setNewChromsPercent(sr.nCromo);
            /**
             * La profundidad minima de un individuo cuando el mundo es creado
             */
            configuracion.setMinInitDepth(sr.minProf);
            
            configuracion.setProgramCreationMaxTries(sr.masInten);
            GPProblem problem = new ProgramacionGenetica(configuracion);
            
            sr.main(s, 0, f, problem);

        } catch (Exception ex) {
            System.out.print("Error en el Hilo");
        }
   }
}
