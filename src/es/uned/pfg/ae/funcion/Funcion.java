package es.uned.pfg.ae.funcion;

/**
 * 
 * @author Fermin Silva
 */
public interface Funcion {

	public void setDimension(int dimension);
	
	
	public int getDimension();

	public double getMin();
	public double getMax();
	
	public double getMinimoGlobal();
	
	public double calcular(double[] valores);
}

