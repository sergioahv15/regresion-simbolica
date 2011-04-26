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
public class Sin { //funcion Sin(a*x)
    public ArbolB sin;

    public Sin(){
        sin=new ArbolB("Sin");
        sin.raiz.der=(new NodoB("*"));
        sin.raiz.der.izq=(new NodoB("@"));
        sin.raiz.der.der=(new NodoB("x"));
    }

}
