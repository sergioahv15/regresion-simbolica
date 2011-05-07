//Clase qeu implementa la funcion de raiz cuadrada la cual se usa para
//Generar el arbol de solucion
package Genetica;

import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.impl.*;
import org.jgap.util.*;


public class Sqrt extends MathCommand implements ICloneable {

  public Sqrt(final GPConfiguration a_conf, Class a_returnType)
      throws InvalidConfigurationException {
    super(a_conf, 1, a_returnType);
  }

  public String toString() {
    return "sqrt";
  }
 
  public String getName() {
    return "Sqrt";
  }

  //Se genera polimorfismo y se usa la funcion primitiva de java para 
  //Obtener la raiz cuadrada
  public float execute_float(ProgramChromosome c, int n, Object[] args) {
    float f = c.execute_float(n, 0, args);
    return (float) Math.sqrt(Math.abs(f));
  }

  public double execute_double(ProgramChromosome c, int n, Object[] args) {
    double d = c.execute_double(n, 0, args);
    return Math.sqrt(Math.abs(d));
  }

  public Object execute_object(ProgramChromosome c, int n, Object[] args) {
    return ( (Compatible) c.execute_object(n, 0, args)).execute_sqrt();
  }

  protected interface Compatible {
    public Object execute_sqrt();
  }
  
  //Se clona el objeto para no dejarlo amarrado a una sola funcion
  public Object clone() {
    try {
      Sqrt result = new Sqrt(getGPConfiguration(), getReturnType());
      return result;
    } catch (Exception ex) {
      throw new CloneException(ex);
    }
  }
}
