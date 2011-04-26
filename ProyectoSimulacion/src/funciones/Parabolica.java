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
public class Parabolica { //funcion x^a
    public ArbolB parabolica;

    public Parabolica(){
        parabolica=new ArbolB('^');
        parabolica.raiz.izq=(new NodoB('x'));
        parabolica.raiz.der=(new NodoB('@'));
        
    }
}
