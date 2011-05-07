//Clase para calcular el fitnees del algoritmo genetico

package Genetica;

import java.util.HashMap;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.gp.terminal.Variable;

public class EvaluarFit 
{  
  //Definir el indice donde se encuentra la variable de salida por omision es la ultima
  public static Integer variableSal;

  //Si encuentra una solucion igual o mejor al error permitido para
  public static boolean maxMejora = false;
 
  //Numero de cols en la matriz no transpuesta
  private int cols;

   //Multiplicador para agrandar y comparar
   public static double mulErr = -1.0d;

   //Si se encontro una solucion terminar el genetico
   public static boolean saltarT = false;

   //Relacion entre resultados si la diferencia es la definida los debe mostrar
   public static Double limMostrar = 0.0000;

   //Numero de columnas
   public static int numCol;

   //Numero de variables de entrada
   public static int varEntrada;

   // Este hash lleva la informacion de todas las posibles soluciones que cumplen
   // con los requisitos del minimo error
   private static HashMap<String, Integer> listSoluciones = new HashMap<String,
      Integer> ();

   //Matriz que contiene los datos
   public static Double[][] datos;

   //Guardar los resultados
   public static Double[] results;

   // Se definen las variables que vamos a usar es posible tener n variables
   // independientes y una dependiente
   public static Variable[] variables;


   //Clase para evaluar el resultado del algoritmo
   public static class EvaluarFitness extends GPFitnessFunction
   {
      //Metodos de la superclase
       protected double evaluate(final IGPProgram a_subject) {
         return calcFit(a_subject);
      }

    public double calcFit(final IGPProgram ind) {
      double error = 0.0;
      Object[] entra = new Object[0];
      
      //Tener los datos en el arreglo de las variables
      for (int j = 0; j < numCol; j++)
      {
         int variableIndex = 0;
         for (int i = 0; i < varEntrada + 1; i++) {
             if (i != variableSal) {
                variables[variableIndex].set(datos[i][j]);
                variableIndex++;
             }
         }

        try {
          double result = ind.execute_double(0, entra);
          results[j] = result;

          //el error se calcula sobre la diferencia entre los daros de entrada
          //y los datos que produce el algortimo genetico si el error es 0 son
          //los mismos datos y por lo tanto la misma funicon
          error += Math.abs(result - datos[variableSal][j]); 
          
          if (Double.isInfinite(error)) {
            return Double.MAX_VALUE;
          }
        } catch (ArithmeticException ex) {
          //Si existe algun error como un desbordamiento o divisiones invalidad
          //se entra a esta exceptio
          System.out.println("Hubo un error en una operacion aritmetica");
          throw ex;
        }
      }
      
      //Si el algoritmo encontro un match perfecto es decir una relacion perfecta
      //Entre los datos que entraron y los datos generados el programa debe de mostrar
      //Las soluciones que cumplan con este requisito y enviar un salto para terminar el
      //Programa
      if (error <= limMostrar && saltarT)
      {
        if (!maxMejora)
          maxMejora = true;

        ProgramChromosome chrom = ind.getChromosome(0);
        String program = chrom.toStringNorm(0);
        if (!listSoluciones.containsKey(program))          
          listSoluciones.put(program, 1);        
        else         
          listSoluciones.put(program, listSoluciones.get(program) + 1);
        
        error = 0.1d;
      }

      //Para evitar errores de precision ampliamos el error multiplicando por una
      //Constante pequeña
      if (mulErr > 0.0d)
        return error * mulErr;
      
      else 
        return error;
     
    }
  }

   //Metodos setters y getters de las variables de la clase
   public void setCols(int cols) {
        this.cols = cols;
    }

    public int getCols() {
        return cols;
    }

    public static void setBumpPerfect(boolean bumpPerfect) {
        EvaluarFit.saltarT = bumpPerfect;
    }

    public static void setBumpValue(Double bumpValue) {
        EvaluarFit.limMostrar = bumpValue;
    }

    public static void setData(Double[][] data) {
        EvaluarFit.datos = data;
    }

    public static void setFoundPerfect(boolean foundPerfect) {
        EvaluarFit.maxMejora = foundPerfect;
    }

    public static void setFoundSolutions(HashMap<String, Integer> foundSolutions) {
        EvaluarFit.listSoluciones = foundSolutions;
    }

    public static void setNumInputVariables(int numInputVariables) {
        EvaluarFit.varEntrada = numInputVariables;
    }

    public static void setNumRows(int numCols) {
        EvaluarFit.numCol = numCols;
    }

    public static void setOutputVariable(Integer outputVariable) {
        EvaluarFit.variableSal = outputVariable;
    }

    public static void setResults(Double[] results) {
        EvaluarFit.results = results;
    }

    public static void setScaleError(double scaleError) {
        EvaluarFit.mulErr = scaleError;
    }

    public static void setVariables(Variable[] variables) {
        EvaluarFit.variables = variables;
    }

    public static boolean isBumpPerfect() {
        return saltarT;
    }

    public static Double getBumpValue() {
        return limMostrar;
    }

    public static Double[][] getData() {
        return datos;
    }

    public static boolean isFoundPerfect() {
        return maxMejora;
    }

    public static HashMap<String, Integer> getFoundSolutions() {
        return listSoluciones;
    }

    public static int getNumInputVariables() {
        return varEntrada;
    }

    public static int getNumRows() {
        return numCol;
    }

    public static Integer getOutputVariable() {
        return variableSal;
    }

    public static Double[] getResults() {
        return results;
    }

    public static double getScaleError() {
        return mulErr;
    }

    public static Variable[] getVariables() {
        return variables;
    }
   
  

}

