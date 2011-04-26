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
public class Exp { //funcion Exp(a*x)
    public ArbolB exp;

    public Exp(){
        exp=new ArbolB("Exp");
        exp.raiz.der=(new NodoB("*"));
        exp.raiz.der.izq=(new NodoB("@"));
        exp.raiz.der.der=(new NodoB("x"));

    }
}
