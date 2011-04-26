package edb.evaluate;

/**
 * Clase encargada de evaluar una expresion que puede contener variables. <br>
 * Las funciones que puede evaluar, son de las clases que implementan la
 * interfaz <code>Funcion</code> y se encuentran en <code>ebd.functions</code>.
 * <br>
 * <br>
 * 
 * 
 *
 * 
 * @version 1.5
 */
public class Evaluate
{
	/**
	 * Operadores soportados, que definen el orden de prioridad <br>
	 */
	private final String OPERS = "+-*/^";

	/**
	 * Caracteres Invalidos como nombre de una funcion. <br>
	 */
	private final String NOCARAC = OPERS + "()";

	/**
	 * Expresion a evaluar. <br>
	 */
	private String expresion = "";

	/**
	 * Nombres de la variales definidas. <br>
	 */
	private String variables[];

	/**
	 * Valores de la variables definidas. <br>
	 */
	private double valores[];

	/**
	 * Retorna el valor de la variable por index <br>
	 * 
	 * @param index -
	 *            index de la variable <br>
	 * @return valor
	 */
	public double getValorVariable(int index)
	{
		return valores[index];
	}

	/**
	 * Asigna elm valor de la variable por el index <br>
	 * 
	 * @param index -
	 *            index de la variable <br>
	 * @param valor -
	 *            valor asignar <br>
	 */
	public void setValorVariable(int index, double valor)
	{
		valores[index] = valor;
	}

	/**
	 * Define la expresion a operar. <br>
	 * 
	 * @param expresion -
	 *            String con la expresion. <br>
	 * @return - true <br>
	 */
	public boolean setExpresion(String expresion)
	{
		if(expresion == null)
			expresion = "";

		this.expresion = expresion;
		variables = null;
		return true;
	}

	/**
	 * Define las variables utlizadas en la expresion, separadas por coma. <br>
	 * Inicializa los valores de las variables en cero. <br>
	 * 
	 * @param variables -
	 *            String con el nombre de las variables separados por comas.
	 *            <br>
	 * @return - true. <br>
	 */
	public boolean setVariables(String variables)
	{
		this.variables = variables.toUpperCase().split(",");

		for(int i = 0; i < this.variables.length; i++)
		{
			this.variables[i] = eliminarEspacios(this.variables[i]);
		}
		valores = new double[this.variables.length];
		return true;
	}

	/**
	 * Define el valor de una variable existente. <br>
	 * 
	 * @param variable -
	 *            nombre de la variable. <br>
	 * @param value -
	 *            valor de la variable <br>
	 * @return - Retorna <code>true</code> si se realizo la asignacion del
	 *         valor. <br>
	 */
	public boolean setVariableValue(String variable, double value)
	{
		if(variables != null)
			for(int i = 0; i < variables.length; i++)
			{
				if(variables[i].equals(eliminarEspacios(variable.toUpperCase())))
				{
					valores[i] = value;
					return true;
				}
			}

		return false;
	}

	/**
	 * Evalua la expresion, utilizando los valores de las constantes ya
	 * definidas. <br>
	 * 
	 * @return - valor de operacion <br>
	 */
	public double evaluate()
	{
		error = false;
		strError = "";

		double ret = evaluate(expresion);

		return (error) ? 0 : ret;
	}

	/**
	 * Evalua una expresion dada. <br>
	 * 
	 * @param expresion -
	 *            expresion a operar. <br>
	 * @return - valor de operacion <br>
	 */
	private double evaluate(String expresion)
	{

		if(error && !allErrors)
			return 0;

		if(expresion.length() == 0)
		{
			strError += "Error: Expresion Vacia!\n";
			error = true;
			return 0;
		}

		expresion = eliminarParentesis(expresion);

		if(expresion.charAt(0) == '-')
		{
			expresion = "0" + expresion;
		}

		// ----------------------------------------------

		int vc = indexVariable(expresion);
		if(vc >= 0)
		{
			return valores[vc];
		}

		// ----------------------------------------------

		if(isNumber(expresion))
		{
			return Double.parseDouble(expresion);
		}

		// ----------------------------------------------

		String fun = dataFunction(expresion);

		if(fun != null)
		{
			Funcion f = null;
			String name = fun.split(";")[0];
			String expresion2 = fun.split(";")[1];
                        
                name=name.toLowerCase();
                
                switch(name.charAt(0))
                {
                    case 'a':
                        name="Abs";
                    break;
                    
                    case 'c':
                         if(name.charAt(name.length()-1)=='h')
                            name="Cosh";
                         else
                            name="Cos";
                    break;
                    
                    case 'l':
                        name="Ln";
                    break;
                    
                     case 'e':
                        name="Exp";
                     break;
                    
                    case 't':
                        if(name.charAt(name.length()-1)=='h')
                            name="Tanh";
                        else
                            name="Tan";
                    break;
                    
                    case 's':
                        if(name.charAt(1)=='i'){
                           
                        if(name.charAt(name.length()-1)=='h')
                            name="Sinh";
                        else{
                            if(name.charAt(2)=='g')
                              name="Sig";
                            else
                              name="Sin";
                        };
                        }else{
                            if(name.charAt(1)=='q')
                             name="Sqrt";
                            
                            
                        };
                        
                    break;
                    
                     
                    
                    /*Funciones adicionales a implementar
                     
                     Senh : Seno Hiperbólico
                     Cosh : Coseno Hiperbólico
                     Tanh : Tangente Hiperbólica
                     Exp  : Exponencial
                     Sig  : Sigmoide
                    */ 
                   
                    
                    
                }; //fin switch
                
                //System.out.println("NAMES ES:c"+name);
                        
                        

			double rr = 0;
			try
			{
				f = (Funcion) Class.forName("edb.functions." + name).newInstance();

			}catch(Exception e)
			{
				error = true;
				strError += "Error:  Funcion no definida \"" + name + "\"\n";
				rr = evaluate(expresion2);
				return 0;
			}

			rr = evaluate(expresion2);

			if(f.isValue(rr))
				return f.evaluate(rr);
			else
			{
				strError += "Error:  Parametro no aceptado \"" + name + "(" + rr + ")\"\n";
				error = true;
				return 0;
			}

		}

		// -----------------------------------------------

		int prioridad = 0;

		int posoper = -1;
		char oper = '?';
		int priop = Integer.MAX_VALUE;
		boolean ispar = false;

		for(int i = 0; i < expresion.length(); i++)
		{
			if(expresion.charAt(i) == '(')
			{
				prioridad++;
				ispar = true;
			}
			if(expresion.charAt(i) == ')')
			{
				prioridad--;
				ispar = true;
			}

			int po = OPERS.indexOf(Character.toString(expresion.charAt(i)));
			if(po >= 0 && po + 10 * prioridad <= priop)
			{
				oper = expresion.charAt(i);
				posoper = i;
				priop = po + 10 * prioridad;
			}
		}

		String s1 = "";
		String s2 = "";

		if(oper == '?')
		{
			error = true;

			if(ispar)
				strError += "Error:  Expresion no definida \"" + expresion + "\"\n";
			else
				strError += "Error:  Variable no definida \"" + expresion + "\"\n";

			return 0;

		}else
		{
			s1 = expresion.substring(0, posoper);
			s2 = expresion.substring(posoper + 1);
		}

		return Operate(s1, expresion.charAt(posoper), s2);
	}

	/**
	 * Indice de la variable. <br>
	 * 
	 * @param expresion -
	 *            nombre de la variable <br>
	 * @return - indice de la variable, si la expresion no es una variable
	 *         retorna -1<br>
	 */
	private int indexVariable(String expresion)
	{
		int index = -1;
		if(variables != null)
			for(int i = 0; i < variables.length; i++)
			{
				if(variables[i].equals(expresion.toUpperCase()))
				{
					index = i;
				}
			}
		return index;
	}

	/**
	 * Realiza una operacion aritmetica entre dos expresiones. <br>
	 * 
	 * @param s1 -
	 *            expresion1 <br>
	 * @param operador -
	 *            Operador <br>
	 * @param s2 -
	 *            expresion2 <br>
	 * @return - valor de la operacion <br>
	 */
	private double Operate(String s1, char operador, String s2)
	{
		double ret = 0;

		double v1 = evaluate(s1);
		double v2 = evaluate(s2);

		switch (operador)
		{
		case '+':
			ret = v1 + v2;
			break;

		case '-':
			ret = v1 - v2;
			break;

		case '*':
			ret = v1 * v2;
			break;

		case '/':
			ret = v1 / v2;
			break;

		case '^':
			ret = Math.pow(v1, v2);
			break;
		}

		return ret;
	}

	/**
	 * Verifica si la expresion es una Funcion y retorna el nombre y la
	 * expresion de parametro separados por punto y coma <br>
	 * 
	 * @param expresion -
	 *            expresion
	 * @return - nombre;expresion
	 */
	private String dataFunction(String expresion)
	{
		int i = expresion.indexOf("(");
		int j = expresion.length() - 1;

		if(expresion.charAt(j) != ')')
			j = -1;

		if(i < 0 || j == -1)
		{
			return null;
		}

		String data = expresion.substring(i + 1, j);
		if(!okParentesis(data))
		{
			return null;
		}

		String name = expresion.substring(0, i);

		for(int k = 0; k < name.length(); k++)
		{
			if(NOCARAC.indexOf(expresion.substring(k, k + 1)) >= 0)
			{
				return null;
			}
		}

		return eliminarEspacios(name) + ";" + data;
	}

	/**
	 * Elimina los parentesis y espacios innecesarios de una expresion. <br>
	 * 
	 * @param str -
	 *            expresion <br>
	 * @return - expresion corregida <br>
	 */
	private String eliminarParentesis(String str)
	{
		str = eliminarEspacios(str);

		if(str.length() > 1)
			while((str.charAt(0) == '(') && (str.charAt(str.length() - 1) == ')'))
			{
				String tt = str.substring(1, str.length() - 1);
				if(tt.length() == 0)
				{

					strError += "Error: Parentesis Vacios! \n";
					error = true;
					return "1";
				}

				if(okParentesis(tt))
				{
					str = tt;
				}else
				{
					return str;
				}
			}
		return str;
	}

	/**
	 * Define si los parentesis de una expresionestan bien! <br>
	 * Una expresion esta bien definida con parentesis, si parea cada parenesis
	 * que se abre existe un parentesis que se cierra. <br>
	 * 
	 * @param str -
	 *            expresion. <br>
	 * @return - Retorna <code>true</code> si esta bien definido.
	 */
	private boolean okParentesis(String str)
	{
		int par = 0;

		for(int i = 0; i < str.length(); i++)
		{
			if(str.charAt(i) == '(')
				par++;
			if(str.charAt(i) == ')')
				par--;

			if(par < 0)
				return false;
		}

		return (par == 0);
	}

	/**
	 * Elimina los espacios iniciales y finales de una expresion. <br>
	 * 
	 * @param str -
	 *            expresion <br>
	 * @return - expresion corregida. <br>
	 */
	private String eliminarEspacios(String str)
	{
		String ret = "";
		int i = 0;
		while(i < str.length() && str.charAt(i) == ' ')
			i++;

		int j = str.length();
		while(j > 0 && str.charAt(j - 1) == ' ')
			j--;

		return str.substring(i, j);
	}

	/**
	 * Define si una expresion es un numero <br>.
	 * 
	 * @param expresion
	 * @return Retorna <code>true</code> si es un numero. <br>
	 */
	private boolean isNumber(String expresion)
	{

		boolean ret = false;
		try
		{
			double rr = Double.parseDouble(expresion);
			ret = true;

		}catch(NumberFormatException e)
		{
		}
		return ret;

	}

	/**
	 * Define si existo algun error en la ultima Evaluacion de expresion
	 * <code>evaluate()</code>.<br>
	 * 
	 * @return - Retorna <code>true</code> si se genero algun error. <br>
	 */
	public boolean isError()
	{
		return error;
	}

	/**
	 * Retorna la cadena con la especificacion de los errores ocurridos en la
	 * evaluacion. <br>
	 * 
	 * @return - Errores ocurridos. <br>
	 */
	public String getErrores()
	{
		return strError;
	}

	/**
	 * Determina si hay error <br>
	 */
	private boolean error;

	/**
	 * String de errores. <br>
	 */
	private String strError;

	/**
	 * Ver todos los errores. <br>
	 * 
	 * @return - AllErrors <br>
	 */
	public boolean getAllErrors()
	{
		return allErrors;
	}

	/**
	 * Define si se van a mostrar todos los errores.
	 * 
	 * @param allErrors -
	 *            boolean <br>
	 * @return -<code>true</code>
	 */
	public boolean setAllErrors(boolean allErrors)
	{
		this.allErrors = allErrors;

		return true;
	}

	/**
	 * Define si se van ha mostrar todos los errores <br>
	 */
	private boolean allErrors = false;

	/**
	 * @return Returns the valores.
	 */
	public double[] getValores()
	{
		return valores;
	}

	/**
	 * @param valores
	 *            The valores to set.
	 */
	public void setValores(double[] valores)
	{
		this.valores = valores;
	}
}