package Genetica;

import Interfaz.Principal;
import java.io.*;
import java.lang.reflect.Array;
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

/*
 *
 * Symbolic Regression in JGAP.
 *
 * This program is based on the JGAP example MathProblem.java with
 * some generality.
 *
 * TODO:
 *  - option for ignoring specific variables
 *  - option for stopping:
 *     - running forever
 *     - after a specific time,
 *     - when a specific fitness value is reached
 *  - calculate the number of data rows automatically (i.e. skip num_row)
 *  - show similiar solutions (with the same fitness as the best)
 *  - check if there are any more parameters in GPConfiguration to handle.
 *  - accept nominal values in the data section; then converted to
 *    numeric values.
 *  - add fitness metrics.
 *  - punish longer solutions
 *  - support for different "main" return classes, i.e. not just DoubleClass
 *  - correlation coefficient, and other statistical measures, e.g.
 *    R-squared, mean squared error, mean absolut error, minimum error,
 *    maximum error
 *  - more/better error checks
 *  - more building blocks, a la Eureqa http://ccsl.mae.cornell.edu/eureqa_ops
 *  - support for derivata (a la Eureqa)? This may be hard...
 *  - integrate with Weka?
 *  - simplify the best solution with a CAS?
 *
 * This program was written by Hakan Kjellerstrand (hakank@bonetmail.com)
 * Also, see my JGAP page http://www.hakank.org/jgap/
 *
 * @since 3.5
 */
public class SymbolicRegression extends GPProblem {

  public static int cols;

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getCols() {
        return cols;
    }
  public static Principal ventana;
  private EvaluarFit es = new EvaluarFit();

  // variable name
  public static String[] variableNames;
  public static int[] ignoreVariables; // TODO
  // constants
  public static ArrayList<Double> constants = new ArrayList<Double> ();

  // If we have found a perfect solution.
  public static boolean foundPerfect = false;

  // standard GP parameters
  public static int minInitDepth = 2;

  public static int maxInitDepth = 4;

  public static int populationSize = 100;

  public static int maxCrossoverDepth = 8;

  public static int programCreationMaxTries = 5;

  public static int numEvolutions = 1800;

  public static boolean verboseOutput = true;

  public static int maxNodes = 21;

  public static double functionProb = 0.9d;

  public static float reproductionProb = 0.1f; // float

  public static float mutationProb = 0.1f; // float

  public static double crossoverProb = 0.9d;

  public static float dynamizeArityProb = 0.08f; // float

  public static double newChromsPercent = 0.3d;

  public static int tournamentSelectorSize = 0;

  // lower/upper ranges for the Terminal
  public static double lowerRange = -10.0d;

  public static double upperRange = 10.0d;

  // Should the terminal be a wholenumber or not?
  public static boolean terminalWholeNumbers = true;

  public static String returnType = "DoubleClass"; // not used yet

  public static String presentation = "";

  // Using ADF
  public static int adfArity = 0;

  public static String adfType = "double";

  public static boolean useADF = false;

  // list of functions (as strings)
  public static String[] functions = {"Multiply", "Divide", "Add", "Subtract"};

  // list of functions for ADF
  public static String[] adfFunctions = {"Multiply3", "Divide", "Add3",
      "Subtract"};

  // Should we punish length of solutions?
  // Note: Very simplistic version.
  // public static boolean punishLength = false;

  // timing
  public static long startTime;

  public static long endTime;

  // if > 0.0d -> stop if the fitness is below or equal
  // this value. TODO!
  public static double stopCriteria = -1.0d;

  public static boolean showPopulation = false;

  public static boolean showSimiliar = false;

  public SymbolicRegression(){
  }

  public SymbolicRegression(GPConfiguration a_conf)
      throws InvalidConfigurationException {
    super(a_conf);
  }


  /**
   * This method is used for setting up the commands and terminals that can be
   * used to solve the problem.
   *
   * @return GPGenotype
   * @throws InvalidConfigurationException
   */
  public GPGenotype create() throws InvalidConfigurationException {
    GPConfiguration conf = getGPConfiguration();
    // At first, we define the return type of the GP program.
    // ------------------------------------------------------
    // Then, we define the arguments of the GP parts. Normally, only for ADF's
    // there is a specification here, otherwise it is empty as in first case.
    // -----------------------------------------------------------------------
    Class[] types;
    Class[][] argTypes;   
    
      types = new Class[] {CommandGene.DoubleClass};
      argTypes = new Class[][] { {} };
    
    // Configure desired minimum number of nodes per sub program.
    // Same as with types: First entry here corresponds with first entry in
    // nodeSets.
    // Configure desired maximum number of nodes per sub program.
    // First entry here corresponds with first entry in nodeSets.
    //
    // This is experimental!
    int[] minDepths;
    int[] maxDepths;
    
    minDepths = new int[] {1};
    maxDepths = new int[] {9};
   
    // Next, we define the set of available GP commands and terminals to use.
    // Please see package org.jgap.gp.function and org.jgap.gp.terminal
    // You can easily add commands and terminals of your own.
    // ----------------------------------------------------------------------
    CommandGene[] commands = makeCommands(conf, functions, lowerRange,
        upperRange, "plain");
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
      String variableName = variableNames[i];
      if (i != es.outputVariable) {
        if (variableNames != null && variableNames.length > 0) {
          variableName = variableNames[i];
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
    // assign the functions/terminals
    // ------------------------------

      ventana.RenovarText("\n"+"Operaciones definidas: ");///
      for (int i = 0; i < command_len; i++) {
      //ResulImp+="" + commands[i];///
      //System.out.println("function1: " + commands[i]);
        System.out.println("comandos "+commands[i]);
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

      nodeSets[0][i + es.varEntrada] = commands[i];
    }

    
    // this is experimental.
    boolean[] full;
    
    full = new boolean[] {true};
    
    boolean[] fullModeAllowed = full;
    // Create genotype with initial population. Here, we use the
    // declarations made above:
    // ----------------------------------------------------------
    return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodeSets,
        maxNodes, verboseOutput);    
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
        //
        // Note: Not all functions are applicable here...
        //
        if ("Multiply".equals(functions[i])) {
          commandsList.add(new Multiply(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Multiply(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Multiply3".equals(functions[i])) {
          commandsList.add(new Multiply3(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Multiply3(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Add".equals(functions[i])) {
          commandsList.add(new Add(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Add(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Divide".equals(functions[i])) {
          commandsList.add(new Divide(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Divide(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Add3".equals(functions[i])) {
          commandsList.add(new Add3(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Add3(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Add4".equals(functions[i])) {
          commandsList.add(new Add4(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Add4(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Subtract".equals(functions[i])) {
          commandsList.add(new Subtract(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Subtract(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Sine".equals(functions[i])) {
          commandsList.add(new Sine(conf, CommandGene.DoubleClass));
        }
        else if ("ArcSine".equals(functions[i])) {
          commandsList.add(new ArcSine(conf, CommandGene.DoubleClass));
        }
        else if ("Tangent".equals(functions[i])) {
          commandsList.add(new Tangent(conf, CommandGene.DoubleClass));
        }
        else if ("ArcTangent".equals(functions[i])) {
          commandsList.add(new ArcTangent(conf, CommandGene.DoubleClass));
        }
        else if ("Cosine".equals(functions[i])) {
          commandsList.add(new Cosine(conf, CommandGene.DoubleClass));
        }
        else if ("ArcCosine".equals(functions[i])) {
          commandsList.add(new ArcCosine(conf, CommandGene.DoubleClass));
        }
        else if ("Exp".equals(functions[i])) {
          commandsList.add(new Exp(conf, CommandGene.DoubleClass));
        }
        else if ("Log".equals(functions[i])) {
          commandsList.add(new Log(conf, CommandGene.DoubleClass));
        }
        else if ("Abs".equals(functions[i])) {
          commandsList.add(new Abs(conf, CommandGene.DoubleClass));
        }
        else if ("Pow".equals(functions[i])) {
          commandsList.add(new Pow(conf, CommandGene.DoubleClass));
        }
        else if ("Round".equals(functions[i])) {
          commandsList.add(new Round(conf, CommandGene.DoubleClass));
        }
        else if ("Ceil".equals(functions[i])) {
          commandsList.add(new Ceil(conf, CommandGene.DoubleClass));
        }
        else if ("Floor".equals(functions[i])) {
          commandsList.add(new Floor(conf, CommandGene.DoubleClass));
        }
        else if ("Modulo".equals(functions[i])) {
          commandsList.add(new Modulo(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Modulo(conf, CommandGene.BooleanClass));
          }
        }
        else if ("ModuloD".equals(functions[i])) {
          commandsList.add(new ModuloD(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new ModuloD(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Max".equals(functions[i])) {
          commandsList.add(new Max(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Max(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Min".equals(functions[i])) {
          commandsList.add(new Min(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Min(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Sqrt".equals(functions[i])) {
          // Note: This uses my Sqrt.java file
          commandsList.add(new Sqrt(conf, CommandGene.DoubleClass));
        }
        else if ("Logistic".equals(functions[i])) {
          // Note: This uses my Logistic.java file
          commandsList.add(new Logistic(conf, CommandGene.DoubleClass));
        }
        else if ("Gaussian".equals(functions[i])) {
          // Note: This uses my Gaussian.java file
          commandsList.add(new Gaussian(conf, CommandGene.DoubleClass));
        }
        else if ("Sigmoid".equals(functions[i])) {
          // Note: This uses my Sigmoid.java file
          commandsList.add(new Sigmoid(conf, CommandGene.DoubleClass));
        }
        else if ("Gamma".equals(functions[i])) {
          // Note: This uses my Gamma.java file
          commandsList.add(new Gamma(conf, CommandGene.DoubleClass));
        }
        else if ("Step".equals(functions[i])) {
          // Note: This uses my Step.java file
          commandsList.add(new Step(conf, CommandGene.DoubleClass));
        }
        else if ("Sign".equals(functions[i])) {
          // Note: This uses my Sign.java file
          commandsList.add(new Sign(conf, CommandGene.DoubleClass));
        }
        else if ("Hill".equals(functions[i])) {
          // Note: This uses my Hill.java file
          commandsList.add(new Hill(conf, CommandGene.DoubleClass));
        }
        else if ("LesserThan".equals(functions[i])) {
          // experimental
          commandsList.add(new LesserThan(conf, CommandGene.BooleanClass));
        }
        else if ("GreaterThan".equals(functions[i])) {
          // experimental
          commandsList.add(new GreaterThan(conf, CommandGene.BooleanClass));
        }
        else if ("If".equals(functions[i])) {
          // Note: This is just If on DoubleClass, not a proper Boolean
          commandsList.add(new If(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new If(conf, CommandGene.BooleanClass));
          }
        }
        else if ("IfElse".equals(functions[i])) {
          commandsList.add(new IfElse(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new IfElse(conf, CommandGene.BooleanClass));
          }
        }
        else if ("IfDyn".equals(functions[i])) {
          // Well, this don't work as expected...
          // System.out.println("IfDyn is not supported yet");
          commandsList.add(new IfDyn(conf, CommandGene.BooleanClass, 1, 1, 5));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new IfDyn(conf, CommandGene.DoubleClass, 1, 1, 5));
          }
        }
        else if ("Loop".equals(functions[i])) { // experimental
          commandsList.add(new Loop(conf, CommandGene.DoubleClass, 3));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Loop(conf, CommandGene.BooleanClass, 3));
          }
        }
        else if ("Equals".equals(functions[i])) {
          // experimental
          // commandsList.add(new Equals(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Equals(conf, CommandGene.BooleanClass));
          }
        }
        else if ("ForXLoop".equals(functions[i])) {
          // experimental
          commandsList.add(new ForXLoop(conf, CommandGene.IntegerClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new ForXLoop(conf, CommandGene.BooleanClass));
          }
          else if (useADF && "integer".equals(adfType)) {
            commandsList.add(new ForXLoop(conf, CommandGene.IntegerClass));
          }
        }
        else if ("ForLoop".equals(functions[i])) {
          // experimental
          commandsList.add(new ForLoop(conf, CommandGene.IntegerClass, 10));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new ForLoop(conf, CommandGene.BooleanClass, 10));
          }
          else if (useADF && "integer".equals(adfType)) {
            commandsList.add(new ForLoop(conf, CommandGene.IntegerClass, 10));
          }
        }
        else if ("Increment".equals(functions[i])) {
          commandsList.add(new Increment(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Increment(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Argument".equals(functions[i])) {
          // experimental
          /*
           commandsList.add(new Argument(conf, 1, CommandGene.DoubleClass));
                               if ("boolean".equals(adfType)) {
           commandsList.add(new Argument(conf, 1, CommandGene.BooleanClass));
                               }
           */

        }
        else if ("StoreTerminal".equals(functions[i])) {
          // experimental
          commandsList.add(new StoreTerminal(conf, "dmem0",
              CommandGene.DoubleClass));
          commandsList.add(new StoreTerminal(conf, "dmem1",
              CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new StoreTerminal(conf, "bmem0",
                CommandGene.DoubleClass));
            commandsList.add(new StoreTerminal(conf, "bmem1",
                CommandGene.DoubleClass));
          }
        }
        else if ("Pop".equals(functions[i])) {
          // experimental
          // commandsList.add(new Pop(conf, CommandGene.DoubleClass));
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Pop(conf, CommandGene.BooleanClass));
          }
        }
        else if ("Push".equals(functions[i])) {
          // experimental
          commandsList.add(new Push(conf, CommandGene.DoubleClass));
        }
        else if ("And".equals(functions[i])) {
          // experimental
          commandsList.add(new And(conf));
        }
        else if ("Or".equals(functions[i])) {
          // experimental
          commandsList.add(new Or(conf));
        }
        else if ("Xor".equals(functions[i])) {
          // experimental
          commandsList.add(new Xor(conf));
        }
        else if ("Not".equals(functions[i])) {
          // experimental
          commandsList.add(new Not(conf));
        }
        else if ("AndD".equals(functions[i])) {
          // experimental
          commandsList.add(new AndD(conf));
        }
        else if ("OrD".equals(functions[i])) {
          // experimental
          commandsList.add(new OrD(conf));
        }
        else if ("XorD".equals(functions[i])) {
          // experimental
          commandsList.add(new XorD(conf));
        }
        else if ("NotD".equals(functions[i])) {
          // experimental
          commandsList.add(new NotD(conf));
        }
        else if ("SubProgram".equals(functions[i])) {
          // experimental
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new SubProgram(conf,
                new Class[] {CommandGene.BooleanClass, CommandGene.BooleanClass}));
            commandsList.add(new SubProgram(conf,
                new Class[] {CommandGene.BooleanClass, CommandGene.BooleanClass,
                CommandGene.BooleanClass}));
          }
          commandsList.add(new SubProgram(conf,
              new Class[] {CommandGene.DoubleClass, CommandGene.DoubleClass}));
          commandsList.add(new SubProgram(conf,
              new Class[] {CommandGene.DoubleClass, CommandGene.DoubleClass,
              CommandGene.DoubleClass}));
        }
        else if ("Tupel".equals(functions[i])) {
          // experimental
          if (useADF && "boolean".equals(adfType)) {
            commandsList.add(new Tupel(conf,
                                       new Class[] {CommandGene.BooleanClass,
                                       CommandGene.BooleanClass}));
          }
        }
        else {
          System.out.println("Unkown function: " + functions[i]);
          System.exit(1);
        }
      }
      commandsList.add(new Terminal(conf, CommandGene.DoubleClass, lowerRange,
                                    upperRange, terminalWholeNumbers));      
     
      if (constants != null) {
        for (int i = 0; i < constants.size(); i++) {
          Double constant = constants.get(i);
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

  /**
   * Starts the example.
   *
   * @author Hakan Kjellerstrand
   */
  public static void main(String[] args, int caso, Principal frame, GPProblem problem) throws Exception {

    // Create the genotype of the problem, i.e., define the GP commands and
    // terminals that can be used, and constrain the structure of the GP
    // program.
    // --------------------------------------------------------------------
    GPGenotype gp = problem.create();
    // gp.setVerboseOutput(true);
    gp.setVerboseOutput(false);
    startTime = System.currentTimeMillis();
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
    if (showSimiliar) {
      similiar = new HashMap<String, Integer> ();
    }
    for (int gen = 1; gen <= numEvolutions; gen++) {
      gp.evolve(); // evolve one generation
      gp.calcFitness();
      GPPopulation pop = gp.getGPPopulation();
      IGPProgram thisFittest = pop.determineFittestProgram();
      // TODO: Here I would like to have the correlation coefficient etc
      thisFittest.setApplicationData( (Object) ("gen" + gen));
      ProgramChromosome chrom = thisFittest.getChromosome(0);
      String program = chrom.toStringNorm(0);
      double fitness = thisFittest.getFitnessValue();
      if (showSimiliar || showPopulation) {
        if (showPopulation) {
          /*ventana.RenovarText("\n"+"Generation " + gen +
                             " (show whole population, sorted)");//
          System.out.println("Generation " + gen +
                             " (show whole population, sorted)");*/
        }
        pop.sortByFitness();
        for (IGPProgram p : pop.getGPPrograms()) {
          double fit = p.getFitnessValue();
          if (showSimiliar && fit <= bestFit) {
            String prog = p.toStringNorm(0);
            if (!similiar.containsKey(prog)) {
              similiar.put(prog, 1);
            }
            else {
              similiar.put(prog, similiar.get(prog) + 1);
            }
          }
          if (showPopulation) {
            String prg = p.toStringNorm(0);
            int sz = p.size();
            /*ventana.RenovarText("\n"+"\tprogram: " + prg + " fitness: " + fit);///
            System.out.println("\tprogram: " + prg + " fitness: " + fit);*/
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
        myOutputSolution(thisFittest, gen);
        bestFit = fitness;
        bestProgram = program;
        fittest = thisFittest;
        if (showSimiliar) {
          // reset the hash
          similiar.clear(); // = new HashMap<String,Integer>();
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
    myOutputSolution(fittest, numEvolutions);
    //ventana.RenovarText("\n"+"applicationData: " + fittest.getApplicationData());///
    //System.out.println("applicationData: " + fittest.getApplicationData());
    // Create a graphical tree of the best solution's program and write it to
    // a PNG file.
    // ----------------------------------------------------------------------
    // problem.showTree(gp.getAllTimeBest(), "mathproblem_best.png");

    endTime = System.currentTimeMillis();
    long elapsedTime = endTime - startTime;
    ventana.RenovarText("\n"+"\nEl tiempo de ejecucion fue: " + elapsedTime + "ms");///
    System.out.println("\nTotal time " + elapsedTime + "ms");
    /*if (showSimiliar) {
      ventana.RenovarText("\n"+"\nAll solutions with the best fitness (" + bestFit +
                         "):");///
      System.out.println("\nAll solutions with the best fitness (" + bestFit +
                         "):");
      // TODO: These should be sorted by values.
      for (String p : similiar.keySet()) {
        ventana.RenovarText("\n"+ p + " (" + similiar.get(p) + ")");///
        System.out.println(p + " (" + similiar.get(p) + ")");
      }
    }*/
    //System.exit(0);
  }
 
  //Resultados del algoritmo
  public static void myOutputSolution(IGPProgram a_best, int gen) throws ScriptException {
    
      /*ResulImp+="Generacion "
                       + (gen)
                       + "/" + numEvolutions;

    /*System.out.println("Evolving generation "
                       + (gen)
                       + "/" + numEvolutions
                       + ", memory free: "
                       + freeMB
                       + " MB");*/
    if (a_best == null) {
      ventana.RenovarText("\n"+"No se encontro una buena solucion tal vez no sea funcion!!");///
      //System.out.println("No best solution (null)");
      return;
    }
    double bestValue = a_best.getFitnessValue();
    if (Double.isInfinite(bestValue)) {
      ventana.RenovarText("\n"+"No se encontro una buena solucion tal vez no sea funcion!!");///
      //System.out.println("No best solution (infinite)");
      return;
    }
    ventana.RenovarText("\n"+"Mejor fitness encontrado: " +
                       NumberKit.niceDecimalNumber(bestValue, 2));///

    //////
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
        double x=ventana.datos[0][j];
        System.out.println(cromo);
        System.out.println(cromocop);
        double y=(Double)engine.eval(cromocop);
        ventana.series2.add(x, y);
    }
    ventana.RenovarImagen();
    /////


    /*System.out.println("Best solution fitness: " +
                       NumberKit.niceDecimalNumber(bestValue, 2));*/
    ventana.RenovarText("\n"+"Mejor Solucion: " + a_best.toStringNorm(0));///
    //System.out.println("Best solution: " + a_best.toStringNorm(0));
    String depths = "";
    int size = a_best.size();
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        depths += " / ";
      }
      depths += a_best.getChromosome(i).getDepth(0);
    }    
  }
}
