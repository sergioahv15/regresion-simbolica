//Clase principal del algoritmo, armamos el arbol en esta clase y lo enviamos
//a evaluar para ver si el arbol generado sirve, en esta clase se definen todas
//las operaciones de los algoritmos geneticos

package Genetica;

import Interfaz.Principal;
import java.util.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jfree.data.xy.XYSeries;

import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.function.*;
import org.jgap.gp.impl.*;
import org.jgap.gp.terminal.*;
import org.jgap.util.*;

public class ProgramacionGenetica extends GPProblem {

  public static int cols;// numero de datos
  public static Principal ventana; //objeto que ayuda a mostrar datos en interfaz
  private EvaluarFit es = new EvaluarFit(); //Objeto que ayuda a evaluar las soluciones

  // variable name
  public static String[] nomVars;
 
  // constants
  public static ArrayList<Double> constantes = new ArrayList<Double> (); 

  //Variables para configurar el algoritmo genetico
  public static int minProf = 2;  //Minima profundidad del arbol
  public static int maxProf = 4;  //Maxima profundidad del arbol
  public static int poblacion = 100; //Numero de individuos
  public static int profCruce = 8; //profundidad maxima para encontrar individuos para el cruce
  public static int masInten = 5;
  public static int generaciones = 1800; //numero generaciones
  public static boolean salida = true;  //mostrar datos
  public static int maxNodos = 21;     //numero maximo de nodos en el arbol
  public static double probFunc = 0.9; //Probabilidad de ser una funcion
  public static float probRepro = (float)0.1;
  public static float probMuta = (float)0.1;
  public static double probCruce = 0.9; 
  public static double nCromo = 0.3;
  public static int tamTorneo = 0;

  // intervalo de busqueda
  public static double comIntervalo = -10.0;
  public static double finIntervalo = 10.0;

  // finalizar el algoritmo con un numero de iteraciones
  public static boolean finalizarIte = true;

  // Funciones basicas
  public static String[] functions = {"Multiply", "Divide", "Add", "Subtract"};  
  
  //Variables para medir el tiempo que se demora el algoritmo
  public static long tInicio;
  public static long tFin;

  //Si hemos encontrado una solucion con un error menor o igual al definido
  //o si el numero de iteraciones es igual al maximo establecido para
  public static double cParada = -1.0;

  //Si existen varias soluciones mostrar todas
  public static boolean verSim = false;

  public ProgramacionGenetica(){
  }

  public ProgramacionGenetica(GPConfiguration a_conf)
      throws InvalidConfigurationException {
    super(a_conf);
  }

 //metodo heredado de la GPProblem se utiliza para configurar los parametros
 //del genetico
 public GPGenotype create() throws InvalidConfigurationException {
    GPConfiguration conf = getGPConfiguration();   
    Class[] types;
    Class[][] argTypes;   
    
    types = new Class[] {CommandGene.DoubleClass};
    argTypes = new Class[][] { {} };
   
    // Next, we define the set of available GP commands and terminals to use.
    // Please see package org.jgap.gp.function and org.jgap.gp.terminal
    // You can easily add commands and terminals of your own.
    // ----------------------------------------------------------------------
    CommandGene[] commands = makeCommands(conf, functions, comIntervalo,
        finIntervalo, "plain");
    // Create the node sets
    int command_len = commands.length;
    CommandGene[][] nodeSets = new CommandGene[2][es.varEntrada +
        command_len];
    // the variables:
    //  1) in the nodeSets matrix
    //  2) as variables (to be used for fitness checking)
    // --------------------------------------------------
    es.variables = new Variable[es.varEntrada];
    es.variables = new Variable[es.varEntrada];
    int variableIndex = 0;
    for (int i = 0; i < es.varEntrada + 1; i++) {
      String variableName = nomVars[i];
      if (i != es.variableSal) {
        if (nomVars != null && nomVars.length > 0) {
          variableName = nomVars[i];
        }
        es.variables[variableIndex] = Variable.create(conf, variableName,
            CommandGene.DoubleClass);
        es.variables[variableIndex] = Variable.create(conf, variableName,
            CommandGene.DoubleClass);
        nodeSets[0][variableIndex] = es.variables[variableIndex];
        ventana.RenovarText("\n"+"Variables de Entrada: " + es.variables[variableIndex]);///
        System.out.println("input variable: " + es.variables[variableIndex]);
        variableIndex++;
      }
    }
    

     //Este ciclo permite mostrar al usuario que funciones se estan usando
     //Para encontrar la solucion.
     ventana.RenovarText("\n"+"Operaciones definidas: ");
      for (int i = 0; i < command_len; i++) {
          if(commands[i].toString().contains("+"))
              ventana.RenovarText("+, ");
          if(commands[i].toString().contains("-")  && !commands[i].toString().contains("&"))
              ventana.RenovarText("C, ");
          if(commands[i].toString().contains("-")  && commands[i].toString().contains("&"))
              ventana.RenovarText("-, ");
          if(commands[i].toString().contains("*"))
              ventana.RenovarText("*, ");
          if(commands[i].toString().contains("/"))
              ventana.RenovarText("/, ");
          if(commands[i].toString().contains("sqrt"))
              ventana.RenovarText("Raiz2, ");
          if(commands[i].toString().contains("^"))
              ventana.RenovarText("^, ");
          if(commands[i].toString().contains("log"))
              ventana.RenovarText("Ln, ");
          if(commands[i].toString().contains("sine"))
              ventana.RenovarText("Sin, ");
          if(commands[i].toString().contains("cosine"))
              ventana.RenovarText("Cos, ");
          if(commands[i].toString().contains("Exp"))
              ventana.RenovarText("Exp, ");

          //Colocar una operacion en el nodo actual del arbol
          nodeSets[0][i + es.varEntrada] = commands[i];
    }
   
    //Comenzando se genera una configuracion al azar para mejorar desde esta
     return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodeSets,
        maxNodos, salida);
  }
  
  public static Double[][] transposeMatrix(Double[][] m) {
    int r = m.length;
    int c = m[0].length;
    Double[][] t = new Double[c][r];
    for (int i = 0; i < r; ++i) {
      for (int j = 0; j < c; ++j) {
        t[j][i] = m[i][j];
      }
    }
    return t;
  } 

  /*
   *  makeCommands:
   *  makes the CommandGene array given the function listed in the
   *  configurations file
   *  ------------------------------------------------------------
   */
  static CommandGene[] makeCommands(GPConfiguration conf, String[] functions,
                                    Double lowerRange, Double upperRange,
                                    String type) {
    ArrayList<CommandGene> commandsList = new ArrayList<CommandGene> ();
    int len = functions.length;
    try {
      for (int i = 0; i < len; i++) {        
        if ("Multiply".equals(functions[i])) 
          commandsList.add(new Multiply(conf, CommandGene.DoubleClass));

        else if ("Add".equals(functions[i]))
          commandsList.add(new Add(conf, CommandGene.DoubleClass));          
       
        else if ("Divide".equals(functions[i])) 
          commandsList.add(new Divide(conf, CommandGene.DoubleClass));
        
        else if ("Subtract".equals(functions[i]))
          commandsList.add(new Subtract(conf, CommandGene.DoubleClass));          
       
        else if ("Sine".equals(functions[i])) 
          commandsList.add(new Sine(conf, CommandGene.DoubleClass)); 
        
        else if ("Cosine".equals(functions[i])) 
          commandsList.add(new Cosine(conf, CommandGene.DoubleClass));       
       
        else if ("Exp".equals(functions[i])) 
          commandsList.add(new Exp(conf, CommandGene.DoubleClass));
        
        else if ("Log".equals(functions[i])) 
          commandsList.add(new Log(conf, CommandGene.DoubleClass));
        
        else if ("Abs".equals(functions[i])) 
          commandsList.add(new Abs(conf, CommandGene.DoubleClass));
        
        else if ("Pow".equals(functions[i])) 
          commandsList.add(new Pow(conf, CommandGene.DoubleClass));       
        
        else if ("Sqrt".equals(functions[i])) 
          commandsList.add(new Sqrt(conf, CommandGene.DoubleClass));   
      }

      commandsList.add(new Terminal(conf, CommandGene.DoubleClass, lowerRange,
                                    upperRange, finalizarIte));
     
      if (constantes != null) {
        for (int i = 0; i < constantes.size(); i++) {
          Double constant = constantes.get(i);
          commandsList.add(new Constant(conf, CommandGene.DoubleClass, constant));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    CommandGene[] commands = new CommandGene[commandsList.size()];
    commandsList.toArray(commands);
    return commands;
  }

  public static void main(String[] args, int caso, Principal frame, GPProblem problem) throws Exception {

    // Create the genotype of the problem, i.e., define the GP commands and
    // terminals that can be used, and constrain the structure of the GP
    // program.
    // --------------------------------------------------------------------
    GPGenotype gp = problem.create();
    // gp.setVerboseOutput(true);
    gp.setVerboseOutput(false);
    tInicio = System.currentTimeMillis();
    // Start the computation with maximum 800 evolutions.
    // if a satisfying result is found (fitness value almost 0), JGAP stops
    // earlier automatically.
    // --------------------------------------------------------------------
    // gp.evolve(numEvolutions);

    //
    // I'm rolling my own to to be able to control output better etc.
    //
    /*ventana.RenovarText("\n"+"Creating initial population");///
    System.out.println("Creating initial population");
    ventana.RenovarText("\n"+"Mem free: "
                       + SystemKit.niceMemory(SystemKit.getTotalMemoryMB()) +
                       " MB");///
    System.out.println("Mem free: "
                       + SystemKit.niceMemory(SystemKit.getTotalMemoryMB()) +
                       " MB");*/
    IGPProgram fittest = null;
    double bestFit = -1.0d;
    String bestProgram = "";
    int bestGen = 0;
    HashMap<String, Integer> similiar = null;
    if (verSim) {
      similiar = new HashMap<String, Integer> ();
    }
    for (int gen = 1; gen <= generaciones; gen++) {
      gp.evolve(); // evolve one generation
      gp.calcFitness();
      GPPopulation pop = gp.getGPPopulation();
      IGPProgram thisFittest = pop.determineFittestProgram();
      // TODO: Here I would like to have the correlation coefficient etc
      thisFittest.setApplicationData( (Object) ("gen" + gen));
      ProgramChromosome chrom = thisFittest.getChromosome(0);
      String program = chrom.toStringNorm(0);
      double fitness = thisFittest.getFitnessValue();
      if (verSim) {
        pop.sortByFitness();
        for (IGPProgram p : pop.getGPPrograms()) {
          double fit = p.getFitnessValue();
          if (verSim && fit <= bestFit) {
            String prog = p.toStringNorm(0);
            if (!similiar.containsKey(prog)) {
              similiar.put(prog, 1);
            }
            else {
              similiar.put(prog, similiar.get(prog) + 1);
            }
          }

        }
      }
      //
      // Yes, I have to think more about this....
      // Right now a program is printed if it has
      // better fitness value than the former best solution.

      // if (gen % 25 == 0) {
      //    myOutputSolution(fittest, gen);
      // }
      if (bestFit < 0.0d || fitness < bestFit) {
        bestGen = gen;
        mostrarDatos(thisFittest, gen);
        bestFit = fitness;
        bestProgram = program;
        fittest = thisFittest;
        if (verSim) {
          similiar.clear();
        }
        // Ensure that the best solution is in the population.
        // gp.addFittestProgram(thisFittest);
      }
      else {
        /*
          if (gen % 25 == 0 && gen != numEvolutions) {
         System.out.println("Generation " + gen + " (This is a keep alive message.)");
            // myOutputSolution(fittest, gen);
                         }
         */
      }
    }

    // Print the best solution so far to the console.
    // ----------------------------------------------
    // gp.outputSolution(gp.getAllTimeBest());

    //ventana.RenovarText("\n"+"\nAll time best (from generation " + bestGen + ")");///
    //System.out.println("\nAll time best (from generation " + bestGen + ")");
    mostrarDatos(fittest, generaciones);
    //ventana.RenovarText("\n"+"applicationData: " + fittest.getApplicationData());///
    //System.out.println("applicationData: " + fittest.getApplicationData());
    // Create a graphical tree of the best solution's program and write it to
    // a PNG file.
    // ----------------------------------------------------------------------
    // problem.showTree(gp.getAllTimeBest(), "mathproblem_best.png");

    tFin = System.currentTimeMillis();    
    ventana.RenovarText("\n"+"\nEl tiempo de ejecucion fue: " + (tFin - tInicio) + "ms");
    
  }

  //Resultados del algoritmo
  public static void mostrarDatos(IGPProgram a_best, int gen) throws ScriptException {
     
    if (a_best == null) {
      ventana.RenovarText("\n"+"No se encontro una buena solucion tal vez no sea funcion!!");      
      return;
    }
    double bestValue = a_best.getFitnessValue();
    if (Double.isInfinite(bestValue)) {
      ventana.RenovarText("\n"+"No se encontro una buena solucion tal vez no sea funcion!!");   
      return;
    }
    ventana.RenovarText("\n"+"Mejor fitness encontrado: " +
                       NumberKit.niceDecimalNumber(bestValue, 2));
   
    ventana.series2 = new  XYSeries("XYGraph");

    for(int j=0;j<cols;j++){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String cromo=a_best.toStringNorm(0);
        String cromocop="";
        cromocop=new Transformar().devolver(" "+cromo, ventana, j);
        while(new Transformar().verificar(cromocop)){
            cromocop=new Transformar().devolver(cromocop, ventana, j);
        }
        double x = ventana.datos[0][j];
        double y = (Double)engine.eval(cromocop);
        ventana.series2.add(x, y);
    }
    ventana.RenovarImagen();    
    ventana.RenovarText("\n"+"Mejor Solucion: " + a_best.toStringNorm(0));    
    
  }

  //Setters y getters
  public void setCols(int cols) {
        this.cols = cols;
    }

    public int getCols() {
        return cols;
    }
}
