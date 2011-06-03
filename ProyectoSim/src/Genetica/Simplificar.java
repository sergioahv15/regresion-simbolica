/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//
package Genetica;

import org.lsmp.djep.sjep.PNodeI;
import org.lsmp.djep.sjep.PolynomialCreator;
import org.lsmp.djep.xjep.XJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

/**
 *
 * @author Diego
 */
public class Simplificar {

    /** Funcion que simplifica una expresion */
    public static String Simplificar(String funcion){
        //Proceso para redefinir la cadena a Djep
        String nuevaCadena="";
        for(int i=0;i<funcion.length();i++){
            if(funcion.charAt(i)=='s' && funcion.charAt(i+1)=='i' && funcion.charAt(i+2)=='n'&& funcion.charAt(i+3)=='e'){
                nuevaCadena+="sin";
                i+=3;
                if(funcion.charAt(i+2)!='('){
                    i+=2;
                    nuevaCadena+="(";
                    int contador=0;
                    while(funcion.charAt(i)=='1' || funcion.charAt(i)=='2' || funcion.charAt(i)=='3' || funcion.charAt(i)=='4' || funcion.charAt(i)=='5' || funcion.charAt(i)=='6' || funcion.charAt(i)=='7' || funcion.charAt(i)=='8' || funcion.charAt(i)=='9' || funcion.charAt(i)=='0' || funcion.charAt(i)=='.' || funcion.charAt(i)=='X' || funcion.charAt(i)=='Y' || funcion.charAt(i)=='V' || funcion.charAt(i)=='-'){
                        nuevaCadena+=funcion.charAt(i);
                        i++;
                    }
                    i--;
                    nuevaCadena+=")";
                }
            }
            else if(funcion.charAt(i) == 'c' && funcion.charAt(i + 1) == 'o' && funcion.charAt(i + 2) == 's' && funcion.charAt(i + 3) == 'i')
            {
                nuevaCadena+="cos";
                i+=5;
                if(funcion.charAt(i+2)!='('){
                    i+=2;
                    nuevaCadena+="(";
                    int contador=0;
                    while(funcion.charAt(i)=='1' || funcion.charAt(i)=='2' || funcion.charAt(i)=='3' || funcion.charAt(i)=='4' || funcion.charAt(i)=='5' || funcion.charAt(i)=='6' || funcion.charAt(i)=='7' || funcion.charAt(i)=='8' || funcion.charAt(i)=='9' || funcion.charAt(i)=='0' || funcion.charAt(i)=='.' || funcion.charAt(i)=='X' || funcion.charAt(i)=='Y' || funcion.charAt(i)=='V' || funcion.charAt(i)=='-'){
                        nuevaCadena+=funcion.charAt(i);
                        i++;
                    }
                    i--;
                    nuevaCadena+=")";
                }
            }
            else if(funcion.charAt(i) == 's' && funcion.charAt(i + 1) == 'q' && funcion.charAt(i + 2) == 'r' && funcion.charAt(i + 3) == 't')
            {
                nuevaCadena+="sqrt";
                i+=3;
                if(funcion.charAt(i+1)!='('){
                    i+=1;
                    nuevaCadena+="(";
                    int contador=0;
                    while(funcion.charAt(i)=='1' || funcion.charAt(i)=='2' || funcion.charAt(i)=='3' || funcion.charAt(i)=='4' || funcion.charAt(i)=='5' || funcion.charAt(i)=='6' || funcion.charAt(i)=='7' || funcion.charAt(i)=='8' || funcion.charAt(i)=='9' || funcion.charAt(i)=='0' || funcion.charAt(i)=='.' || funcion.charAt(i)=='X' || funcion.charAt(i)=='Y' || funcion.charAt(i)=='V' || funcion.charAt(i)=='-'){
                        nuevaCadena+=funcion.charAt(i);
                        i++;
                    }
                    i--;
                    nuevaCadena+=")";
                }
            }
            else if(funcion.charAt(i) == 'l' && funcion.charAt(i + 1) == 'o' && funcion.charAt(i + 2) == 'g')
            {
                nuevaCadena+="log";
                i+=2;
                if(funcion.charAt(i+2)!='('){
                    i+=2;
                    nuevaCadena+="(";
                    int contador=0;
                    while(funcion.charAt(i)=='1' || funcion.charAt(i)=='2' || funcion.charAt(i)=='3' || funcion.charAt(i)=='4' || funcion.charAt(i)=='5' || funcion.charAt(i)=='6' || funcion.charAt(i)=='7' || funcion.charAt(i)=='8' || funcion.charAt(i)=='9' || funcion.charAt(i)=='0' || funcion.charAt(i)=='.' || funcion.charAt(i)=='X' || funcion.charAt(i)=='Y' || funcion.charAt(i)=='V' || funcion.charAt(i)=='-'){
                        nuevaCadena+=funcion.charAt(i);
                        i++;
                    }
                    i--;
                    nuevaCadena+=")";
                }
            }
            else if(funcion.charAt(i) == 'E' && funcion.charAt(i + 1) == 'x' && funcion.charAt(i + 2) == 'p')
            {
                nuevaCadena+="exp";
                i+=2;
            }
            else
                nuevaCadena+=funcion.charAt(i);
        }

        //Proceso de Xjep y Sjep
        XJep j = new XJep();
        j.addStandardConstants();
        j.addStandardFunctions();
        j.addComplex();
        j.setAllowUndeclared(true);
        j.setImplicitMul(true);
        j.setAllowAssignment(true);
        PNodeI poly = null;
        try {
            Node node=j.parse(nuevaCadena);
            PolynomialCreator pc = new PolynomialCreator(j);
            node = pc.simplify(node);
            node = pc.expand(node);
            poly = pc.createPoly(node);
	} catch (ParseException e) {
            System.out.println("Error en la simplificacion");
        } catch (Exception e) {
            System.out.println("Error en la simplificacion");
        }
        return poly.toString();
    }

}
