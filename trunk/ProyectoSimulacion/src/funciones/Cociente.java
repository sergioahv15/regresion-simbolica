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
public class Cociente { //funcion a/x
    public ArbolB cociente;

    public Cociente(){
        cociente=new ArbolB('/');
        cociente.raiz.izq=(new NodoB('@'));
        cociente.raiz.der=(new NodoB('x'));
    }
}
