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

  //Tenemos constructores con polimorfismo este constructor no recibe ni realiza
  //Ninguna accion
  public ProgramacionGenetica(){
  }

  //Este constructor verifica que la configuracion que se le ha dado al algoritmo
  //sea correcta
  public ProgramacionGenetica(GPConfiguration config)
      throws InvalidConfigurationException {
      super(config);
  }

 //metodo heredado de la GPProblem se utiliza para configurar los parametros
 //del genetico
 public GPGenotype create() throws InvalidConfigurationException {
    int ind = 0;

    Class[] clases;
    Class[][] clasesP;
    GPConfiguration config = getGPConfiguration();

    //Variables para inicializar el genotipo
    clases = new Class[] {CommandGene.DoubleClass};
    clasesP = new Class[][] {{}};
   
    //Configuramos las funciones que vamos a usar para generar los arboles y
    //el intervalo para comparar los datos
    CommandGene[] comandos = generarC(config, functions, comIntervalo, finIntervalo, "plain");
   
    //Se crean los nodos
    CommandGene[][] nodos = new CommandGene[2][es.varEntrada + comandos.length];
    
    //Se asignan al vector varialbes las varialbes de entrada
    es.variables = new Variable[es.varEntrada];
    
    for (int i = 0; i < es.varEntrada + 1; i++) {
      String nomvar = nomVars[i];
      
      //Si es variable de entrada
      if (i != es.variableSal)
      {
        if (nomVars != null && nomVars.length > 0)
          nomvar = nomVars[i];
      
        es.variables[ind] = Variable.create(config, nomvar,CommandGene.DoubleClass);
        es.variables[ind] = Variable.create(config, nomvar,CommandGene.DoubleClass);
        nodos[0][ind] = es.variables[ind];
        ventana.RenovarText("\n"+"Variables de Entrada: " + es.variables[ind]);
        ind++;
      }
    }

     //Este ciclo permite mostrar al usuario que funciones se estan usando
     //Para encontrar la solucion.
     ventana.RenovarText("\n"+"Operaciones definidas: ");
      for (int i = 0; i < comandos.length; i++) {
          if(comandos[i].toString().contains("+"))
              ventana.RenovarText("+, ");
          if(comandos[i].toString().contains("-")  && !comandos[i].toString().contains("&"))
              ventana.RenovarText("C, ");
          if(comandos[i].toString().contains("-")  && comandos[i].toString().contains("&"))
              ventana.RenovarText("-, ");
          if(comandos[i].toString().contains("*"))
              ventana.RenovarText("*, ");
          if(comandos[i].toString().contains("/"))
              ventana.RenovarText("/, ");
          if(comandos[i].toString().contains("sqrt"))
              ventana.RenovarText("Raiz2, ");
          if(comandos[i].toString().contains("^"))
              ventana.RenovarText("^, ");
          if(comandos[i].toString().contains("log"))
              ventana.RenovarText("Ln, ");
          if(comandos[i].toString().contains("sine"))
              ventana.RenovarText("Sin, ");
          if(comandos[i].toString().contains("cosine"))
              ventana.RenovarText("Cos, ");
          if(comandos[i].toString().contains("Exp"))
              ventana.RenovarText("Exp, ");

          //Colocar una operacion en el nodo actual del arbol
          nodos[0][i + es.varEntrada] = comandos[i];
     }
   
    //Comenzando se genera una configuracion al azar para mejorar desde esta
     return GPGenotype.randomInitialGenotype(config, clases, clasesP, nodos, maxNodos, salida);
  }
 
 //Añadir las funciones con las que contamos a los comandos que puede usar el programa
 //para añdir mas funciones se debe generar un nombre y definir la funcion o
 //usar las funciones que define java
 public  static CommandGene[] generarC(GPConfiguration config,
                              String[] func, Double iniInterval, Double finInterval, String tipo) {
    
    //Arreglo que guarda las funciones que vamos a usar 
    ArrayList<CommandGene> comandos = new ArrayList<CommandGene> ();
   
    //Ciclo que nos permite añadir las funciones que hemos definido
    try {
      for (int i = 0; i < functions.length; i++) {
        if ("Multiply".equals(func[i])) 
          comandos.add(new Multiply(config, CommandGene.DoubleClass));

        else if ("Add".equals(func[i]))
          comandos.add(new Add(config, CommandGene.DoubleClass));
       
        else if ("Divide".equals(func[i])) 
          comandos.add(new Divide(config, CommandGene.DoubleClass));
        
        else if ("Subtract".equals(func[i]))
          comandos.add(new Subtract(config, CommandGene.DoubleClass));
       
        else if ("Sine".equals(func[i])) 
          comandos.add(new Sine(config, CommandGene.DoubleClass));
        
        else if ("Cosine".equals(func[i])) 
          comandos.add(new Cosine(config, CommandGene.DoubleClass));
       
        else if ("Exp".equals(func[i])) 
          comandos.add(new Exp(config, CommandGene.DoubleClass));
        
        else if ("Log".equals(func[i])) 
          comandos.add(new Log(config, CommandGene.DoubleClass));
        
        else if ("Abs".equals(func[i])) 
          comandos.add(new Abs(config, CommandGene.DoubleClass));
        
        else if ("Pow".equals(func[i])) 
          comandos.add(new Pow(config, CommandGene.DoubleClass));
        
        else if ("Sqrt".equals(func[i])) 
          comandos.add(new Sqrt(config, CommandGene.DoubleClass));
      }

      comandos.add(new Terminal(config, CommandGene.DoubleClass, iniInterval,
                                    finInterval, finalizarIte));
     
      //Si podemos meter constantes para generar la funcion
      if (constantes != null) {
        for (int i = 0; i < constantes.size(); i++) {
          Double constant = constantes.get(i);
          comandos.add(new Constant(config, CommandGene.DoubleClass, constant));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Ocurrio un error intentando meter las funciones para operar");
    }
    
    CommandGene[] com = new CommandGene[comandos.size()];
    comandos.toArray(com);
    return com;
  }

 //Funcion que crea el genotipo y define todo lo necesario para empezar el genetico
 public static void main(String[] args, int caso, Principal frame, GPProblem problem) throws Exception {
    //Crear el genotipo para el problema
    GPGenotype gp = problem.create();   
    gp.setVerboseOutput(false);
    double maxFit = -1.0;

    //Pedir el tiempo para tener idea de cuanto se demora el algoritmo
    tInicio = System.currentTimeMillis();
   
    IGPProgram test = null;
    IGPProgram fActual;
    //map que guarda las soluciones si tienen un fitness igual o mejor que el error permitido
    HashMap<String, Integer> similiar = null;

    //Si queremos ver los resultados instanciamos el map
    if (verSim)
      similiar = new HashMap<String, Integer> ();
    
    for (int gen = 1; gen <= generaciones; gen++)
    {
       gp.evolve();//Generamos la evolucion
       gp.calcFitness();  //Calcular el fitnees

       //Obtener la poblacion y encontrar para cada individuo su fitness
       GPPopulation poblac = gp.getGPPopulation();
       fActual = poblac.determineFittestProgram();
      
       fActual.setApplicationData( (Object) ("gen" + gen));             
       double fitness = fActual.getFitnessValue();
       
       //Si se desean ver resultados que sean iguales al mejor que se ha encontrado
       if (verSim)
       {
          //ordenar la poblacion por fitness de menor a mayo
          poblac.sortByFitness();

          //Ciclo que va atravez de todos los objetos que estan en la poblacion
          for (IGPProgram p : poblac.getGPPrograms()) {
             double fit = p.getFitnessValue();
             
             //Guardar soluciones si las condiciones se cumplen
             if (verSim && fit <= maxFit)
             {
                String prog = p.toStringNorm(0);

                if (!similiar.containsKey(prog))
                   similiar.put(prog, 1);
           
                else
                   similiar.put(prog, similiar.get(prog) + 1);
             }
          }
       }
      
      //Mostramos los datos si el fitness actual es mejor que el anterior
      if (maxFit < 0.0d || fitness < maxFit) {
         maxFit = gen;
         mostrarDatos(fActual, gen);
         maxFit = fitness;
         test = fActual;

        //Limpiar la estructura para guardar las siguientes soluciones
        if (verSim)
          similiar.clear();        
      }      
    }
    mostrarDatos(test, generaciones);
    
    //Pedir el tiempo para restarlo con el que se pidio al principio y asi saber
    //Cuanto se demora el algo
    tFin = System.currentTimeMillis();
    ventana.RenovarText("\n"+"\nEl tiempo de ejecucion fue: " + (tFin - tInicio) + "ms");
    
  }

  //Resultados de el algoritmo para mostrar
  public static void mostrarDatos(IGPProgram mejor, int gen) throws ScriptException {
     
    if (mejor == null) {
      ventana.RenovarText("\n"+"No se encontro una buena solucion tal vez no sea funcion!!");      
      return;
    }
    
    //Si el fitnees es muy grande la solucion es muy mala y se muestra el mensaje
    if (Double.isInfinite(mejor.getFitnessValue())) {
      ventana.RenovarText("\n"+"No se encontro una buena solucion tal vez no sea funcion!!");   
      return;
    }
    //Se muestra el mejor fitnness encontrado hasta el momento
    ventana.RenovarText("\n"+"Mejor fitness encontrado: " + NumberKit.niceDecimalNumber(mejor.getFitnessValue(), 2));
    ventana.series2 = new  XYSeries("XYGraph");

    //Pintamos los datos de la funcion que tenemos actualmente esto ocurre
    //si y solo si los datos estan en dos dimensiones
    for(int j=0;j<cols;j++){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String cromo=mejor.toStringNorm(0);
        String cromocop="";
        cromocop=new Transformar().devolver(" "+cromo, ventana, j);
        while(new Transformar().verificar(cromocop)){
            cromocop=new Transformar().devolver(cromocop, ventana, j);
        }
        double x = ventana.datos[0][j];
        double y = (Double)engine.eval(cromocop);
        ventana.series2.add(x, y);
    }

    //Mostramos la grafica de la funcion que tenemos actualmente
    ventana.RenovarImagen();
    ventana.RenovarText("\n"+"Mejor Solucion: " + mejor.toStringNorm(0));
    
  }

  //Setters y getters
  public void setCols(int cols) {
        this.cols = cols;
    }

    public int getCols() {
        return cols;
    }
}
