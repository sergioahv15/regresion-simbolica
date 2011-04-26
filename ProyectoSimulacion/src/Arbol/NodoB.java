/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Arbol;


/**
 *
 * @author Diego
 */
public class NodoB {

    public Object d;
    public NodoB izq;
    public NodoB der;
    static public String imp;
    public NodoB() {this(null);}

    public NodoB(Object o) {
        this(o,null,null);
        imp="";
    }

    public NodoB(Object o, NodoB i, NodoB d){
        this.d=o; izq=i; der=d;
    }

    static int size (NodoB a){
        if (a==null)
            return 0;
        else
            return 1+size(a.izq)+size(a.der);
    }

    static int height (NodoB a){
        if (a==null)
            return (-1);
        else
            return 1+Math.max(height(a.izq),height(a.der));
    }

    public void preorder (){
        System.out.println(d);
        if (izq != null)
            izq.preorder();
        if (der != null)
            der.preorder();
    }

    public void inorder (){
        imp+='(';
        if (izq != null)
            izq.inorder();
        imp+=d;
        //System.out.println(imp);
        if (der != null)
            der.inorder();
        imp+=')';
    }
    
    public void postorder (){
        if (izq != null)
            izq.postorder();
        if (der != null)
            der.postorder();
        System.out.println(d);
    }

}
