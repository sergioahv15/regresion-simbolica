/*
 * Created on 29-nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edb.alggen;

/**
 * @author Edd
 *  
 */
public class Individuo
{

	public Individuo(double[] cromosoma)
	{
		this.cromosoma = cromosoma;
	}

	public double[] getCromosoma()
	{
		return cromosoma;
	}

	public void setCromosoma(double[] cromosoma)
	{
		this.cromosoma = cromosoma;
	}

	public int getTamaño()
	{
		return tamaño;
	}

	public void setTamaño(int tamaño)
	{
		this.tamaño = tamaño;
	}

	private int tamaño;

	private double cromosoma[];
	
	private double rendimiento;

	
	public double getRendimiento()
	{
		return rendimiento;
	}
	public void setRendimiento(double rendimiento)
	{
		this.rendimiento = rendimiento;
	}
}