/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package funciones;

import Arbol.ArbolB;
import Arbol.NodoB;

/**
 *
 * @author Diego
 */
public class Lineal { //funcion a*x+b
    public ArbolB lineal;

    public Lineal(){
        lineal=new ArbolB('+');
        lineal.raiz.izq=(new NodoB('*'));
        lineal.raiz.izq.izq=(new NodoB('@'));
        lineal.raiz.izq.der=(new NodoB('x'));
        lineal.raiz.der=(new NodoB('@'));
    }

}
