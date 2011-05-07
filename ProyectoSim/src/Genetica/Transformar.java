package Genetica;

import Interfaz.Principal;

public class Transformar {

    public Transformar(){
    }

    public boolean verificar(String cromo){
        for(int i=0;i<cromo.length();i++){
            if(cromo.charAt(i) == 's' && cromo.charAt(i+1) == 'q' && cromo.charAt(i-1)!='.') return true;
            if(cromo.charAt(i) == 's' && cromo.charAt(i+1) == 'i' && cromo.charAt(i-1)!='.') return true;
            if(cromo.charAt(i) == 'c' && cromo.charAt(i+1) == 'o' && cromo.charAt(i-1)!='.') return true;
            if(cromo.charAt(i) == 'l' && cromo.charAt(i+1) == 'o' && cromo.charAt(i-1)!='.') return true;
            if(cromo.charAt(i) == 'E' && cromo.charAt(i+1) == 'x' && cromo.charAt(i-1)!='.') return true;
            if(cromo.charAt(i) == '^') return true;
        }
        return false;
    }

    public String devolver(String cromo, Principal ventana, int j){
        String cromocop="";
        for(int i=0;i<cromo.length();i++){
                if(cromo.charAt(i)=='X') cromocop+=ventana.datos[0][j];

                else if(cromo.charAt(i) == 's' && cromo.charAt(i+1) == 'q' && cromo.charAt(i-1)!='.') {
                    cromocop+="Math.sqrt";
                    if(cromo.charAt(i+5)!='('){
                        cromocop+="(";
                        int contador=0;
                        for(int k=i+5;k<cromo.length() && cromo.charAt(k)!=')';k++){
                            if(cromo.charAt(k)=='X') cromocop+=ventana.datos[0][j];
                            else cromocop += cromo.charAt(k);
                            contador++;
                        }
                        i+=contador;
                        cromocop+=")";
                    }
                    i+=4;
                }

                else if(cromo.charAt(i) == 's' && cromo.charAt(i+1) == 'i' && cromo.charAt(i-1)!='.') {
                    cromocop+="Math.sin";
                    if(cromo.charAt(i+5)!='('){
                        cromocop+="(";
                        int contador=0;
                        for(int k=i+5;k<cromo.length() && cromo.charAt(k)!=')';k++){
                            if(cromo.charAt(k)=='X') cromocop+=ventana.datos[0][j];
                            else cromocop += cromo.charAt(k);
                            contador++;
                        }
                        i+=contador;
                        cromocop+=")";
                    }
                    i+=4;
                }

                else if(cromo.charAt(i) == 'c' && cromo.charAt(i+1) == 'o' && cromo.charAt(i-1)!='.') {
                    cromocop+="Math.cos";
                    if(cromo.charAt(i+7)!='('){
                        cromocop+="(";
                        int contador=0;
                        for(int k=i+7;k<cromo.length() && cromo.charAt(k)!=')';k++){
                            if(cromo.charAt(k)=='X') cromocop+=ventana.datos[0][j];
                            else cromocop += cromo.charAt(k);
                            contador++;
                        }
                        i+=contador;
                        cromocop+=")";
                    }
                    i+=6;
                }

                else if(cromo.charAt(i) == 'l' && cromo.charAt(i+1) == 'o' && cromo.charAt(i-1)!='.') {
                    cromocop+="Math.log";
                    if(cromo.charAt(i+4)!='('){
                        cromocop+="(";
                        int contador=0;
                        for(int k=i+4;k<cromo.length() && cromo.charAt(k)!=')';k++){
                            if(cromo.charAt(k)=='X') cromocop+=ventana.datos[0][j];
                            else cromocop += cromo.charAt(k);
                            contador++;
                        }
                        i+=contador;
                        cromocop+=")/Math.log(2.71828182845905)";
                    }
                    i+=3;
                }

                else if(cromo.charAt(i) == 'E' && cromo.charAt(i+1) == 'x' && cromo.charAt(i-1)!='.') {
                    cromocop+="Math.exp";
                    i+=2;
                }

                else if(cromo.charAt(i) == '^') {
                    String temporal="";
                    int parentesis=0;
                    for(int k=cromocop.length()-1;parentesis>-1 && k>-1;k--){
                        if(cromocop.charAt(k)==')') {
                            temporal += cromocop.charAt(k);
                            parentesis++;
                        }
                        else if(cromocop.charAt(k)=='(') {
                            if(parentesis>0) temporal += cromocop.charAt(k);
                            parentesis--;
                        }
                        else temporal += cromocop.charAt(k);
                    }
                    StringBuilder builder=new StringBuilder(temporal);
                    temporal=builder.reverse().toString();
                    cromocop=cromocop.substring(0, cromocop.length()-temporal.length());
                    cromocop+="Math.pow("+temporal+",";

                    int contador=0;
                    parentesis=0;
                    for(int k=i+1;parentesis>-1 && k<cromo.length();k++){
                        if(cromo.charAt(k)=='X') cromocop+=ventana.datos[0][j];
                        else if(cromo.charAt(k)=='('){
                            cromocop += cromo.charAt(k);
                            parentesis++;
                        }
                        else if(cromo.charAt(k)==')'){
                            if(parentesis>0)cromocop += cromo.charAt(k);
                            else if(parentesis==0) contador--;
                            parentesis--;
                        }
                        else cromocop += cromo.charAt(k);
                        contador++;
                    }

                    cromocop+=")";
                    i+=contador;
                }

                else cromocop+=cromo.charAt(i);
            }
        return cromocop;
    }

}
