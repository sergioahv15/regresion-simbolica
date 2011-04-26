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
public class Polinomica { //funcion x^a+x+b
    public ArbolB poli;

    public Polinomica(){
        poli=new ArbolB('+');
        poli.raiz.izq=(new NodoB('^'));
        poli.raiz.izq.izq=(new NodoB('x'));
        poli.raiz.izq.der=(new NodoB('@'));
        poli.raiz.der=(new NodoB('+'));
        poli.raiz.der.izq=(new NodoB('x'));
        poli.raiz.der.der=(new NodoB('@'));
    }

}
