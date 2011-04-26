package edb.functions;

import edb.evaluate.Funcion;

/**
 * 
 * @version 0.1 <br>
 */
public class Sin implements Funcion
{

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#evaluate(double)
     */
    public double evaluate(double value)
    {
        return Math.sin(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edb.functions.Funcion#isValue(double)
     */
    public boolean isValue(double value)
    {
        boolean ret = true;

        try
        {
            double rr = Math.sin(value);
        } catch (Exception e)
        {
            ret = false;
        }

        return ret;
    }
}
