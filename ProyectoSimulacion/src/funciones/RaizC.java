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
public class RaizC { //funcion Sqrt(a*x)
    public ArbolB raiz;

    public RaizC(){
        raiz=new ArbolB("Sqrt");
        raiz.raiz.der=(new NodoB("*"));
        raiz.raiz.der.izq=(new NodoB("@"));
        raiz.raiz.der.der=(new NodoB("x"));
    }

}
