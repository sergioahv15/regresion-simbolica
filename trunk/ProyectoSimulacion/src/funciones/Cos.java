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
public class Cos { //funcion Cos(a*x)
    public ArbolB cos;

    public Cos(){
        cos=new ArbolB("Cos");
        cos.raiz.der=(new NodoB("*"));
        cos.raiz.der.izq=(new NodoB("@"));
        cos.raiz.der.der=(new NodoB("x"));
    }
}
