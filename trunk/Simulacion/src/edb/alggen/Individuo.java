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

	public int getTama�o()
	{
		return tama�o;
	}

	public void setTama�o(int tama�o)
	{
		this.tama�o = tama�o;
	}

	private int tama�o;

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