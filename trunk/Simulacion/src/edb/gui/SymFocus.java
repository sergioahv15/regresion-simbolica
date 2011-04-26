/*
 * SymFocus.java
 *
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package edb.gui;
import java.io.IOException;
import javax.swing.JOptionPane;
import edb.gui.MainFrame;

/**
 *
 */

//Esta es la clase para manejar el foco
	public class SymFocus extends java.awt.event.FocusAdapter
	{
		
          private javax.swing.JTextField expresion;
          private javax.swing.JTextField constantes;
          private javax.swing.JTextField variables;
          private javax.swing.JTextArea vectores;
          private javax.swing.JEditorPane ayuda;
          private javax.swing.JScrollPane scroll;
            
         public SymFocus(MainFrame mf){
             
             expresion=mf.texpresion;
             constantes=mf.tconstantes;
             variables=mf.tvariables;
             vectores=mf.vectores;
             ayuda=mf.help;
             scroll=mf.scroll2;

         }
             //Metodo que sobreescribimos de la clase FocusAdapter
		public void focusGained(java.awt.event.FocusEvent event)
		{
			Object object = event.getSource();
			//Sobre el objeto event, llamamos al metodo que nos da quien tiene
			//el foco, comparamos con nuestro componentes y ejecutamos el metodo
			//correspondiente
                   
			if (object == expresion)
				expresion_FocusGained(event);
                        
			else if (object == constantes)
				constantes_FocusGained(event);
			else if (object == variables)
				variables_FocusGained(event);
                        
                        else if (object == vectores)
				vectores_FocusGained(event);
		}
                
                
         /*Mètodos para substituir el texto del JEditorPane de la ayuda
         en la parte derecha de la primera pestaña, según el evento 
         Originado en el campo de texto que tenga el foco*/
                
        void expresion_FocusGained(java.awt.event.FocusEvent event)
	{
	  
          scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_AS_NEEDED);
          //scroll.setCorner(scroll.UPPER_RIGHT_CORNER,ayuda);
         
          
          ayuda.setText("      FUNCIÓN A EVALUAR\n\n");
          ayuda.setText(ayuda.getText()+" En ésta casilla debe ingresar la\n");
          ayuda.setText(ayuda.getText()+" función que evaluará el algoritmo\n");
          ayuda.setText(ayuda.getText()+" genético. Recuerde las reglas a\n");
          ayuda.setText(ayuda.getText()+" seguir para ingresar la función,\n");
          ayuda.setText(ayuda.getText()+" así como el uso adecuado de los\n");
          ayuda.setText(ayuda.getText()+" paréntesis. Toda expresión debe ir\n");
          ayuda.setText(ayuda.getText()+" precedida por su función a evaluar\n");
          ayuda.setText(ayuda.getText()+" entre paréntesis ( ). Las funciones\n");
          ayuda.setText(ayuda.getText()+" que se pueden evaluar son:\n\n");
          ayuda.setText(ayuda.getText()+" 1. TRIGONOMETRICAS\n");
          ayuda.setText(ayuda.getText()+" 1.1 Seno:\n");
          ayuda.setText(ayuda.getText()+"   -   Sin(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 1.2 Coseno:\n");
          ayuda.setText(ayuda.getText()+"   -   Cos(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 1.3 Tangente:\n");
          ayuda.setText(ayuda.getText()+"   - Tan(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 2. HIPERBOLICAS\n");
          ayuda.setText(ayuda.getText()+" 2.1 Seno Hiperbólico:\n");
          ayuda.setText(ayuda.getText()+"   -   Sinh(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 2.2 Coseno Hiperbólico:\n");
          ayuda.setText(ayuda.getText()+"   -   Cosh(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 2.3 Tangente Hiperbólica:\n");
          ayuda.setText(ayuda.getText()+"   -   Tanh(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 3. EXPONENCIAL\n");
          ayuda.setText(ayuda.getText()+" 3.1 Función \"e\":\n");
          ayuda.setText(ayuda.getText()+"   -   Exp(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 4. LOGARÍTMICA\n");
          ayuda.setText(ayuda.getText()+" 4.1 Logaritmo Natural:\n");
          ayuda.setText(ayuda.getText()+"   -   Ln(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 5. POTENCIAS\n");
          ayuda.setText(ayuda.getText()+" 5.1 Función Potencia:\n");
          ayuda.setText(ayuda.getText()+"   -   <base>^<exponente>\n\n");
          ayuda.setText(ayuda.getText()+" 6. VALOR ABSOLUTO\n");
          ayuda.setText(ayuda.getText()+" 6.1 Función | |:\n");
          ayuda.setText(ayuda.getText()+"   -   Abs(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 7. RAÍCES\n");
          ayuda.setText(ayuda.getText()+" 7.1 Función raíz cuadrada:\n");
          ayuda.setText(ayuda.getText()+"   -   Sqrt(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" 8. SIGMOIDE\n");
          ayuda.setText(ayuda.getText()+" 8.1 Función Sigmoidal:\n");
          ayuda.setText(ayuda.getText()+"   -   Sig(<expresion>)\n\n");
          ayuda.setText(ayuda.getText()+" Para el aplicativo, es indiferente\n");
          ayuda.setText(ayuda.getText()+" El uso de mayúsculas y minúsculas");
          ayuda.setText(ayuda.getText()+" en los nombres de las funciones.\n\n");
          ayuda.setText(ayuda.getText()+"     OPERADORES\n\n");
          ayuda.setText(ayuda.getText()+" Recuerde además que es posible\n");
          ayuda.setText(ayuda.getText()+" evaluar funciones más complejas\n");
          ayuda.setText(ayuda.getText()+" combinando las anteriores, con\n");
          ayuda.setText(ayuda.getText()+" el uso de los operadores:\n\n");
          ayuda.setText(ayuda.getText()+" a) SUMA: +\n");
          ayuda.setText(ayuda.getText()+" b) RESTA: -\n");
          ayuda.setText(ayuda.getText()+" c) PRODUCTO: *\n");
          ayuda.setText(ayuda.getText()+" d) COCIENTE: /\n");
          ayuda.setText(ayuda.getText()+" e) POTENCIA: ^\n");
          
       
	}
        
        
        void constantes_FocusGained(java.awt.event.FocusEvent event)
	{
	  ayuda.setText("      CONSTANTES A HALLAR\n\n");
          ayuda.setText(ayuda.getText()+" En ésta casilla debe digitar las\n");
          ayuda.setText(ayuda.getText()+" constantes de la función que el\n");
          ayuda.setText(ayuda.getText()+" aplicativo deberá estimar.\n");
          ayuda.setText(ayuda.getText()+" Para ingresarlas, es necesario\n");
          ayuda.setText(ayuda.getText()+" utilizar los mismos símbolos de las");
          ayuda.setText(ayuda.getText()+" constantes que se emplearon para\n");
          ayuda.setText(ayuda.getText()+" ingresar la función. Deben\n");
          ayuda.setText(ayuda.getText()+" introducirse todas las constantes\n");
          ayuda.setText(ayuda.getText()+" separándolas con una coma (,).\n\n");
          ayuda.setText(ayuda.getText()+" Ejemplo: Las constantes son\n");
          ayuda.setText(ayuda.getText()+" a,b,c,d\n");
          ayuda.setText(ayuda.getText()+" Para la función:\n");
          ayuda.setText(ayuda.getText()+" a*(x1^2)+Abs(Cos(b*x2))-c*Ln(1/x3)\n");
	  ayuda.setText(ayuda.getText()+" +Sig(d*x4)\n\n");
          
          
          
	}
        
        
        void variables_FocusGained(java.awt.event.FocusEvent event)
	{
	  ayuda.setText("      VARIABLES DE LA FUNCIÓN\n\n");
          ayuda.setText(ayuda.getText()+" En ésta casilla debe ingresar las\n");
          ayuda.setText(ayuda.getText()+" variables de la función según\n");
          ayuda.setText(ayuda.getText()+" la dimensión de la misma.\n");
          ayuda.setText(ayuda.getText()+" Para ingresarlas, es necesario\n");
          ayuda.setText(ayuda.getText()+" utilizar los mismos símbolos de las");
          ayuda.setText(ayuda.getText()+" variables que se emplearon para\n");
          ayuda.setText(ayuda.getText()+" ingresar la función. Deben\n");
          ayuda.setText(ayuda.getText()+" introducirse todas las variables\n");
          ayuda.setText(ayuda.getText()+" separándolas con una coma (,).\n\n");
          ayuda.setText(ayuda.getText()+" Ejemplo: Las variables son\n");
          ayuda.setText(ayuda.getText()+" x1,x2,x3,x4\n");
          ayuda.setText(ayuda.getText()+" en el caso de una función cuya\n");
          ayuda.setText(ayuda.getText()+" dimensión es 4, como por ejemplo:\n\n");
          ayuda.setText(ayuda.getText()+" a*(x1^2)+Abs(Cos(b*x2))-c*Ln(1/x3)\n");
	  ayuda.setText(ayuda.getText()+" +Sig(d*x4)");
        }
        
         void vectores_FocusGained(java.awt.event.FocusEvent event)
	{
	  ayuda.setText("      VECTORES INICIALES\n\n");
          ayuda.setText(ayuda.getText()+" En ésta casilla debe escribirse los\n");
          ayuda.setText(ayuda.getText()+" vectores iniciales de la función,\n");
          ayuda.setText(ayuda.getText()+" según la dimensión de la misma.\n");
          ayuda.setText(ayuda.getText()+" Para ingresarlos, es necesario\n");
          ayuda.setText(ayuda.getText()+" escribir cada vector en un renglón\n");
          ayuda.setText(ayuda.getText()+" diferente, y también separar los\n");
          ayuda.setText(ayuda.getText()+" componentes del vector con una\n");
          ayuda.setText(ayuda.getText()+" coma ( , ).\n");
          ayuda.setText(ayuda.getText()+" Es importante escribir los valores\n");
          ayuda.setText(ayuda.getText()+" en el mismo orden en el que se\n");
          ayuda.setText(ayuda.getText()+" ingresaron las variables, y\n");
          ayuda.setText(ayuda.getText()+" además la última componente\n");
          ayuda.setText(ayuda.getText()+" será siempre el valor de la función\n");
          ayuda.setText(ayuda.getText()+" evaluada en el mismo vector.\n\n");
          ayuda.setText(ayuda.getText()+" Ejemplo:\n");
          ayuda.setText(ayuda.getText()+" Si las variables de la función son\n");
          ayuda.setText(ayuda.getText()+" x1,x2 entonces algún vector inicial\n");
          ayuda.setText(ayuda.getText()+" se debe escribir de la siguiente\n");
          ayuda.setText(ayuda.getText()+" manera en un renglón aparte:\n\n");
          ayuda.setText(ayuda.getText()+" 0,1,0 <-- {x1}, {x2}, {valor funcion}\n\n");
          ayuda.setText(ayuda.getText()+" Pueden introducirse tantos vectores");
          ayuda.setText(ayuda.getText()+" como se desee, pero al menos\n");
          ayuda.setText(ayuda.getText()+" debe haber un vector inicial.\n");
	}


	}
