/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectosimulacion;

import Arbol.ArbolB;

/**
 *
 * @author Diego
 */
public class Funcion {
    public ArbolB arbol;
    public String resultado;
    public double error;

    public Funcion(ArbolB a, String res, double e){
        arbol=a;
        resultado=res;
        error=e;
    }
}
