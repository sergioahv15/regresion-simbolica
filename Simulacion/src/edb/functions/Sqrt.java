package edb.functions;

import edb.evaluate.Funcion;

/**
 * @version 0.1 <br>
 */
public class Sqrt implements Funcion
{

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#evaluate(double)
     */
    public double evaluate(double value)
    {
        return Math.sqrt(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#isValue(double)
     */
    public boolean isValue(double value)
    {
        return (value >= 0);
    }
}
