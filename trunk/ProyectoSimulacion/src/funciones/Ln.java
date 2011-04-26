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
public class Ln { //funcion Ln(a*x)
    public ArbolB ln;

    public Ln(){
        ln=new ArbolB("Ln");
        ln.raiz.der=(new NodoB('*'));
        ln.raiz.der.izq=(new NodoB("@"));
        ln.raiz.der.der=(new NodoB("x"));

    }
}
