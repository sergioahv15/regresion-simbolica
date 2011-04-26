package edb.alggen;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;

import javax.swing.JOptionPane;

import edb.evaluate.Evaluate;
import edb.gui.MainFrame;
import edb.gui.VentanaAlterna;

/**
 * @author edd
 * 
 */
public class Poblacion
{
    
    public Resultado resultado;

    //Trae una instancia de la clase MainFrame  para la captura de los datos
    
    private MainFrame mf;
    
    private double probabilidadCruce;

    private double probabilidadMutacion;

    private int cantidadPoblacion;

    private int numeroGeneraciones;

    private String archivoEntrada;

    private String archivoSalida;

    private boolean autoPoblacionInicial;

    private double desde;

    private double hasta;

    private String archivoPoblacionInicial;

    private int tipoCalculoError;

    private double errorMinimo;

    private double errorMaximo;

    private double deltaError;

    private double porcPuntosks;

    private String constantes = "";

    private String variables = "";

    private String expresion = "";
    
    private String vectores ="";
    
    private VentanaAlterna ventana;

    // -----------------------------

    private Evaluate evaluate = new Evaluate();

    private Individuo[] poblacion;

    public Poblacion(MainFrame mf, String funcion,String variables,String constantes,String vect,double probabilidadCruce, double probabilidadMutacion, int cantidadPoblacion,
            int numeroGeneraciones, String archivoEntrada, String archivoSalida, boolean autoPoblacionInicial,
            double desde, double hasta, String archivoPoblacionInicial, int tipoCalculoError, double errorMinimo,
            double errorMaximo, double deltaError, double porcPuntosks,VentanaAlterna vent)
    {
        this.probabilidadCruce = probabilidadCruce;
        this.probabilidadMutacion = probabilidadMutacion;
        this.cantidadPoblacion = cantidadPoblacion;
        this.numeroGeneraciones = numeroGeneraciones;
        this.archivoEntrada = archivoEntrada;
        this.archivoSalida = archivoSalida;
        this.autoPoblacionInicial = autoPoblacionInicial;
        this.desde = desde;
        this.hasta = hasta;
        this.archivoPoblacionInicial = archivoPoblacionInicial;
        this.tipoCalculoError = tipoCalculoError;
        this.errorMinimo = errorMinimo;
        this.errorMaximo = errorMaximo;
        this.deltaError = deltaError;
        this.porcPuntosks = porcPuntosks;
        this.constantes = constantes;
        this.variables = variables;
        this.expresion = funcion;
        this.mf = mf;
        this.vectores = vect;
        
        
  

    }

    // **************************************************************************************

    protected Resultado run() // OK
    {

        minimoErrorPoblacion = Double.MAX_VALUE;

        // LeerValores de archivo
        
       if(vectores.length()>=1 && expresion.length()>=1 && constantes.length()>=1 && variables.length()>=1){  
        
        if(leerPuntosArchivo()==true)
        {

            // Calcular Poblacion Inicial...
            poblacionInicial();
            calcularRendimientoIndividuos();
            ventana=new VentanaAlterna(numConstantes, nombresConstantes);

            for (int _i = 0; minimoErrorPoblacion > errorMinimo && _i < numeroGeneraciones; _i++)
            {
                //evaluate.setIndice(0);
                ordenarRendimiento();
                evolucionarPoblacion();
                generarMutaciones();
                calcularRendimientoIndividuos();
                mf.setEstado(Integer.toString(_i + 1) + " / " + numeroGeneraciones);
                mf.barra.setForeground(new java.awt.Color(0,100,200));
                mf.barra.setVisible(true);
                mf.Etiq.setVisible(true);
                mf.barra.setStringPainted(true);
                
                float factor=((_i+1)*100)/numeroGeneraciones;
                    
                mf.barra.setValue(Math.round(factor));
                //ventana.setVisible(true);
                double cromo[] = poblacion[indexMejorIndividuo].getCromosoma();
                double errorc = poblacion[indexMejorIndividuo].getRendimiento();
                ventana.setActualizar(cromo, errorc);
                
                //ventana.setVisible(false);
//                int indice=mf.TabFolders.getSelectedIndex(); 
//         
//               System.out.print("indice pestaña="+indice+"\n");
            }


            // PresentarResultados
            //poblacion[indexMejorIndividuo]
            presentarResultado(poblacion[indexMejorIndividuo]);
        }

        mf.activar();
       }else
         mf.bEvolucion.setEnabled(false);
        if( !(vectores.length()>=1 && expresion.length()>=1 && constantes.length()>=1 && variables.length()>=1)){ 
          JOptionPane.showMessageDialog(null, "Se ha detectado que uno o varios campos están vacíos.\nPor favor revise los datos ingresados en la pestaña\n\"Parámetros de Entrada\"","Error de lectura de datos", JOptionPane.ERROR_MESSAGE);
          mf.bEvolucion.setEnabled(true);
          
        };
        return resultado;
    }

    private boolean leerPuntosArchivo() // OK
    {

        if(vectores.length()>=1 && expresion.length()>=1 && constantes.length()>=1 && variables.length()>=1)
        {
//            FileReader fr = new FileReader(archivoEntrada);
//            LineNumberReader Inr = new LineNumberReader(fr);
            String s[]=new String[vectores.split("\n").length];
            int ind;
            
            //String s;
            numPuntos = 0;

            /*expresion = Inr.readLine();
            variables = Inr.readLine();
            constantes = Inr.readLine();*/
       

            evaluate.setExpresion(expresion);

            evaluate.setVariables(constantes + ", " + variables);

            //System.out.println(constantes + ", " + variables);

            nombresVariables = variables.split(",");
            numVariables = nombresVariables.length;

            nombresConstantes = constantes.split(",");
            numConstantes = nombresConstantes.length;

//            while ((s = Inr.readLine()) != null)
//            {
//                if(!s.equals(""))
//                {
//                    int ss = s.split(",").length;
//                    //System.out.print("ss es:"+ss+"\n");
//                    double[] tt = new double[ss];
//                    for (int i = 0; i < ss; i++)
//                    {
//                        tt[i] = Double.parseDouble(s.split(",")[i]);
//                        System.out.print(tt[i] + " ");
//                    }
//                    System.out.println("");
//
//                    puntos[numPuntos] = tt;
//                    System.out.println("puntos [" + numPuntos + "]=" + puntos[numPuntos]+"\n");
//                     System.out.println("numPuntos es:"+numPuntos+"\n");
//                      numPuntos++;
//                }
//            }
//            
//            s=vectores;
//            String cadena[]=new String[s.split("\n").length];
//            cadena = s.split("\n");
//            for (int i = 0; i < s.split("\n").length; i++){
//              System.out.print("el split "+i+" es:"+cadena[i]+"\n");
//            }
            
            //Lectura de los vectores iniciales desde la primera pestaña
            
           
        s = vectores.split("\n");
      //if(s.length>=1 && expresion.length()>=1 && constantes.length()>=1 && variables.length()>=1){
            
        for(ind=0;ind<s.length;ind++){
            
          
                    if(s[ind] != "")
                    {
                        int ss = s[ind].split(",").length;
                        //System.out.print("ss es:"+ss+"\n");
                        double[] tt = new double[ss];
                        for (int i = 0; i < ss; i++)
                        {
                            tt[i] = Double.parseDouble(s[ind].split(",")[i]);
                            //System.out.print(tt[i] + " ");
                        }
                        //System.out.println("");

                        puntos[numPuntos] = tt;
                        //System.out.println("puntos [" + numPuntos + "]=" + puntos[numPuntos]+"\n");
                        numPuntos++;
                        //System.out.println("numPuntos es:"+numPuntos+"\n");
                    }
             
            
            } //fin for  
//      }else{
//            
//            JOptionPane.showMessageDialog(null, "Error de lectura en los datos de entrada..","Favor revisar los datos ingresados!", JOptionPane.ERROR_MESSAGE);
//            return false;
//      };
            
//            Inr.close();
//            fr.close();

            return true;

        }else
        {
//            JOptionPane.showMessageDialog(null, "Error de lectura en el archivo..\"" + archivoEntrada + "\"",
//                    "Leer Puntos del archivo!", JOptionPane.ERROR_MESSAGE);
           JOptionPane.showMessageDialog(null, "Error de lectura en los datos de entrada..","Favor revisar los datos ingresados!", JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    private int numPuntos;

    private void generarMutaciones()
    {

        Random rd = new Random();
        for (int i = 0; i < cantidadPoblacion; i++)
        {
            if(rd.nextDouble() <= probabilidadMutacion)
            {
                double[] cromo = poblacion[i].getCromosoma();

                int j = (int) (numConstantes * rd.nextDouble());

                double ch = cromo[j] * (rd.nextDouble() - 0.5d) / 10;
                cromo[j] += ch;

            }
        }
    }

    private void calcularRendimientoIndividuos()
    {
        indexMejorIndividuo = 0;

        double mejor = Double.MAX_VALUE;

        for (int i = 0; i < cantidadPoblacion; i++)
        {
            double cromo[] = poblacion[i].getCromosoma();
            double rendimiento = Double.MAX_VALUE;

            switch (tipoCalculoError)
            {
            case 2:
                rendimiento = errorCM(cromo);
                break;
            case 1:
                rendimiento = errorKS(cromo);
                break;
            }
            if(rendimiento < mejor)
            {
                mejor = rendimiento;
                indexMejorIndividuo = i;
            }

            poblacion[i].setRendimiento(rendimiento);
        }

        minimoErrorPoblacion = mejor;

    }

    private double errorCM(double[] cromo)
    {

        double sum = 0;
        for (int k = 0; k < numPuntos; k++)
        {
            int i = 0;
            while (i < numConstantes)
            {
                evaluate.setValorVariable(i, cromo[i]);
                i++;
            }

            int j = 0;
            while (j < numVariables)
            {
                evaluate.setValorVariable(i, puntos[k][j]);
                i++;
                j++;
            }
            
            //System.out.println("puntos ["+ k +"]["+ numVariables +"] es:"+puntos[k][numVariables]+"\n");

            sum += Math.pow(evaluate.evaluate() - puntos[k][numVariables], 2);

        }

        return (sum / numPuntos);
    }

    private double errorKS(double[] cromo)
    {

        double sum = 0;
        double max = 0;
        Random rd = new Random();

        for (int k = 0; k < numPuntos; k++)
        {
            if(rd.nextDouble() < porcPuntosks)
            {
                int i = 0;
                while (i < numConstantes)
                {
                    evaluate.setValorVariable(i, cromo[i]);
                    i++;
                }

                int j = 0;
                while (j < numVariables)
                {
                    evaluate.setValorVariable(i, puntos[k][j]);
                    i++;
                    j++;
                }

                double tmp = Math.abs(evaluate.evaluate() - puntos[k][numVariables]);
                if(tmp > max)
                {
                    max = tmp;
                }
            }
        }

        return max;
    }

    private void presentarResultado(Individuo individuo)
    {
        String res = "";
        ventana.removeAll();
        //ventana.setDefaultCloseOperation(2);
//         ventana.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ventana.setVisible(false);

        double cromo[] = poblacion[indexMejorIndividuo].getCromosoma();

        res += "Expresion = " + expresion + "\n\n";
        res += "Variables = " + variables + "\n";
        res += "Constantes = " + constantes + "\n\n";

        for (int i = 0; i < cromo.length; i++)
        {
            res += nombresConstantes[i] + " = " + cromo[i] + "\n";
        }

        res += "Error = " + poblacion[indexMejorIndividuo].getRendimiento();

        resultado=new Resultado(res,poblacion[indexMejorIndividuo].getRendimiento());

        //JOptionPane.showMessageDialog(null, res, "Solución Encontrada", JOptionPane.INFORMATION_MESSAGE);
//        mf.barra.setVisible(false);
//        mf.Etiq.setVisible(false);
//        mf.texpresion.setEnabled(false);
//        mf.tvariables.setEnabled(false);
//        mf.tconstantes.setEnabled(false);
//         vectores.setEnabled(false);
        //mf.Habilitar();
        try
        {
            FileWriter fr = new FileWriter(archivoSalida);
            fr.write(res);
            fr.close();

        } catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error de escritura en el archivo..\"" + archivoSalida + "\"",
                    "Leer Puntos del archivo!", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void evolucionarPoblacion()
    {

        int cant = (int) (probabilidadCruce * cantidadPoblacion);
        int cp = cantidadPoblacion - 1;

        for (int i = 0; i < cant; i++)
        {
            poblacion[cp - i].setCromosoma(crossover(poblacion[i].getCromosoma(), poblacion[i + 1].getCromosoma()));
        }

    }

    /**
     * 
     */
    private void ordenarRendimiento()
    {
        for (int i = 0; i < cantidadPoblacion - 1; i++)
        {
            if(poblacion[i].getRendimiento() > poblacion[i + 1].getRendimiento())
            {
                Individuo temp = poblacion[i];
                poblacion[i] = poblacion[i + 1];
                poblacion[i + 1] = temp;
                if(i > 0)
                {
                    i -= 2;
                }
            }
        }
    }

    /**
     * @param cromosoma
     * @param cromosoma2
     * @return
     */
    private double[] crossover(double[] cromosoma, double[] cromosoma2)
    {
        int len = cromosoma.length;
        double cromo[] = new double[len];

        for (int i = 0; i < len; i++)
        {
            cromo[i] = (cromosoma[i] + cromosoma2[i]) / 2;
        }

        return cromo;
    }

    private void poblacionInicial()
    {
        Random rd = new Random();

        // Poblacion Automatica
        if(autoPoblacionInicial)
        {
            poblacion = new Individuo[cantidadPoblacion];

            // Genero cada individuo
            for (int i = 0; i < cantidadPoblacion; i++)
            {
                double[] cromo = new double[numConstantes];

                // Genero el cromosoma del individuo con la función percentil de la distribución uniforme
                
                for (int j = 0; j < numConstantes; j++)
                {
                    cromo[j] = (hasta - desde) * rd.nextDouble() + desde;
                }

                poblacion[i] = new Individuo(cromo);
            }

        } else
        {
            try
            {
                FileReader fr = new FileReader(archivoPoblacionInicial);
                LineNumberReader Inr = new LineNumberReader(fr);
                cantidadPoblacion = 0;

                String s;

                while ((s = Inr.readLine()) != null)
                {
                    cantidadPoblacion++;
                }

                Inr.close();
                fr.close();

                fr = new FileReader(archivoPoblacionInicial);
                Inr = new LineNumberReader(fr);

                poblacion = new Individuo[cantidadPoblacion];

                int lineNum = 0;
                int j = 0;

                while ((s = Inr.readLine()) != null)
                {
                    int ss = s.split(",").length;
                    double[] cromo = new double[ss];

                    for (int i = 0; i < ss; i++)
                    {
                        cromo[i] = Double.parseDouble(s.split(",")[i]);
                        System.out.print("/n" + cromo[i] + " ");
                    }
                    System.out.println("");

                    poblacion[j] = new Individuo(cromo);
                    j++;
                }
                Inr.close();
                fr.close();

            } catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error de lectura en el archivo..\"" + archivoPoblacionInicial
                        + "\"", "Leer Puntos del archivo!", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    private double minimoErrorPoblacion;

    private int indexMejorIndividuo;

    private String[] nombresVariables;

    private int numVariables;

    private String[] nombresConstantes;

    public int numConstantes;

    private double[][] puntos = new double[100000][];

    // **************************************************************************************

    public Resultado start()
    {
        return run();
        /*Thread tt = new Thread(new startOperation(this));
        tt.setPriority(Thread.MIN_PRIORITY);
        tt.start();*/
    }



}

class startOperation implements Runnable
{

    public startOperation(Poblacion operation)
    {
        this.operation = operation;
    }

    public void run()
    {
        operation.run();
    }

    private Poblacion operation;

}