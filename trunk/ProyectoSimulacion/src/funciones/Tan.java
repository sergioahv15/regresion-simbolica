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
public class Tan { //funcion Tan(a*x)
    public ArbolB tan;

    public Tan(){
        tan=new ArbolB("Tan");
        tan.raiz.der=(new NodoB("*"));
        tan.raiz.der.izq=(new NodoB("@"));
        tan.raiz.der.der=(new NodoB("x"));
    }

}
