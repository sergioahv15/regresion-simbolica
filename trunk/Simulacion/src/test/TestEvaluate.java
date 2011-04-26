package test;

import edb.evaluate.Evaluate;

/**
 * @author Edd
 */
public class TestEvaluate
{
    public static void main(String[] args)
    {
        Evaluate eva = new Evaluate();
        // Es solamente para que muestre todos los errores...(continuar a pesar
        // de los errores)
        eva.setAllErrors(true);

        // Defino la expresion
        eva.setExpresion("34.765 -Abs ( -daniel + Cos( Ln (Edwin fabian * Claudia paola) + Diana Carlota ^ 2)/ Daniel )");

        // Defino las variables de la expresion separados por coma!
        eva.setVariables("edwin fabian , diana carlota,daniel, Claudia paola ");

        // defino el valor para cada una de las variables
        System.out.println("setVariableValue : " + eva.setVariableValue("edwin fabian ", 1.23));
        System.out.println("setVariableValue : " + eva.setVariableValue("Claudia Paola", 7.31));
        System.out.println("setVariableValue : " + eva.setVariableValue(" diana carlota  ", -5.23));
        System.out.println("setVariableValue : " + eva.setVariableValue("Daniel ", 0.1));

        System.out.println("-----------------------------------------");
        
        // evaluo!
        double re = eva.evaluate();

        // verifico que no haya error!
        if(!eva.isError())
        {
            // Imprimo resultado
            System.out.println("Result = " + re);
        } else
        {
            // Imprimir los Errores generados!
            System.out.println(eva.getErrores());
        }
    }
}