//Clase para calcular el fitnees del algoritmo genetico

package Genetica;

import java.util.HashMap;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.gp.terminal.Variable;

public class EvaluarFit 
{
   // index of the output variable
  public static Integer outputVariable; // default last

   // If we have found a perfect solution.
  public static boolean foundPerfect = false;

    public static void setBumpPerfect(boolean bumpPerfect) {
        EvaluarFit.bumpPerfect = bumpPerfect;
    }

    public static void setBumpValue(Double bumpValue) {
        EvaluarFit.bumpValue = bumpValue;
    }

    public static void setData(Double[][] data) {
        EvaluarFit.datos = data;
    }

    public static void setFoundPerfect(boolean foundPerfect) {
        EvaluarFit.foundPerfect = foundPerfect;
    }

    public static void setFoundSolutions(HashMap<String, Integer> foundSolutions) {
        EvaluarFit.foundSolutions = foundSolutions;
    }

    public static void setNumInputVariables(int numInputVariables) {
        EvaluarFit.varEntrada = numInputVariables;
    }

    public static void setNumRows(int numCols) {
        EvaluarFit.numCol = numCols;
    }

    public static void setOutputVariable(Integer outputVariable) {
        EvaluarFit.outputVariable = outputVariable;
    }

    public static void setResults(Double[] results) {
        EvaluarFit.results = results;
    }

    public static void setScaleError(double scaleError) {
        EvaluarFit.scaleError = scaleError;
    }

    public static void setVariables(Variable[] variables) {
        EvaluarFit.variables = variables;
    }

    public static boolean isBumpPerfect() {
        return bumpPerfect;
    }

    public static Double getBumpValue() {
        return bumpValue;
    }

    public static Double[][] getData() {
        return datos;
    }

    public static boolean isFoundPerfect() {
        return foundPerfect;
    }

    public static HashMap<String, Integer> getFoundSolutions() {
        return foundSolutions;
    }

    public static int getNumInputVariables() {
        return varEntrada;
    }

    public static int getNumRows() {
        return numCol;
    }

    public static Integer getOutputVariable() {
        return outputVariable;
    }

    public static Double[] getResults() {
        return results;
    }

    public static double getScaleError() {
        return scaleError;
    }

    public static Variable[] getVariables() {
        return variables;
    }

   // if the values are too small we may want to scale
   // the error
   public static double scaleError = -1.0d;

    // "bumping" is when we found a "perfect solution" and
   // want to see more "perfect solutions"
   public static boolean bumpPerfect = false;

   // the limit for which we should show all (different) solutions
   public static Double bumpValue = 0.0000;

   public static int numCol;
   public static int varEntrada;

   // checks for already shown solution when bumping
   private static HashMap<String, Integer> foundSolutions = new HashMap<String,
      Integer> ();

   // the data (as Double)
   // Note: the last row is the output variable per default
   public static Double[][] datos;

   //Cadena para imprimir en la interface
   public static Double[] results;

   // the variables to use (of size numInputVariables)
   public static Variable[] variables;

   public EvaluarFit()
   {
   }
      
   public static class EvaluarFitness extends GPFitnessFunction
   {
   protected double evaluate(final IGPProgram a_subject) {
      return calcFit(a_subject);
    }

    public double calcFit(final IGPProgram ind) {
      double error = 0.0f;
      Object[] noargs = new Object[0];
      // Evaluate function for the input numbers
      // --------------------------------------------
      // double[] results  =  new double[numRows];
      for (int j = 0; j < numCol; j++) {
        // Provide the variable X with the input number.
        // See method create(), declaration of "nodeSets" for where X is
        // defined.
        // -------------------------------------------------------------

        // set all the input variables
        int variableIndex = 0;
        for (int i = 0; i < varEntrada + 1; i++) {
          if (i != outputVariable) {
            variables[variableIndex].set(datos[i][j]);
            variableIndex++;
          }
        }
        try {
          double result = ind.execute_double(0, noargs);
          results[j] = result;

          // Sum up the error between actual and expected result to get a defect
          // rate.
          // -------------------------------------------------------------------

          // hakank: TODO: test with different metrics...
          error += Math.abs(result - datos[outputVariable][j]); // original
          // error += Math.pow(Math.abs(result - data[outputVariable][j]),2);

          // If the error is too high, stop evaluation and return worst error
          // possible.
          // ----------------------------------------------------------------
          if (Double.isInfinite(error)) {
            return Double.MAX_VALUE;
          }
        } catch (ArithmeticException ex) {
          // This should not happen, some illegal operation was executed.
          // ------------------------------------------------------------
          System.out.println(ind);
          throw ex;
        }
      }
      /*
        // experimental
        ProgramChromosome chrom = ind.getChromosome(0);
        String program = chrom.toStringNorm(0);
        double length = program.length();
       */

      // If the fitness is very close to 0.0 then we maybe bump it
      // up to see alternative solutions.
      // -------------------------------------------------------
      if (error <= bumpValue && bumpPerfect) {
        if (!foundPerfect)
          foundPerfect = true;

        ProgramChromosome chrom = ind.getChromosome(0);
        String program = chrom.toStringNorm(0);
        if (!foundSolutions.containsKey(program)) {
          System.out.println("PROGRAM:" + program + " error: " + error);
          foundSolutions.put(program, 1);
        }
        else {
          // TODO: We may want to show the number of hits
          // after the run...
          foundSolutions.put(program, foundSolutions.get(program) + 1);
        }
        error = 0.1d;
      }

      if (scaleError > 0.0d) {
        return error * scaleError;
      }
      else {
        return error;
      }
    }
  }

}

