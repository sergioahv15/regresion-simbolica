/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Arbol;

/**
 *
 * @author Diego
 */
public class ArbolB {
    public NodoB raiz;

    public ArbolB() {
        raiz=null;
    }

    public ArbolB(Object o){
        raiz=new NodoB(o);
    }

    public int size(){
        return NodoB.size(raiz);
    }

    public int height(){
        return NodoB.height(raiz);
    }

    public void preorder (){
        if (raiz!=null) raiz.preorder();
    }

    public String inorder (){
        if (raiz!=null) raiz.inorder();
        return raiz.imp;
    }

    public void postorder (){
        if (raiz!=null) raiz.postorder();
    }
    
}
