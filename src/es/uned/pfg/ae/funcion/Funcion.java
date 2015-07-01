package es.uned.pfg.ae.funcion;

/**
 * Interfaz generica que deben seguir todas las funciones para ser optimizadas
 * por el algoritmo.
 *
 * @author Fermin Silva
 */
public interface Funcion {

	/**
	 * Asigna la dimension especificada a la funcion, que debe coincidir con
	 * la cantidad de genes de los individuos.
	 */
	public void setDimension(int dimension);
	
	
	public int getDimension();

	/**
	 * Devuelve el valor minimo de las coordenadas de entrada (el dominio de
	 * la funcion)
	 */
	public double getMin();

	/**
	 * Devuelve el valor maximo de las coordenadas de entrada (el dominio de
	 * la funcion)
	 */
	public double getMax();

	/**
	 * Devuelve el minimo global de la funcion, ya que para las pruebas se
	 * utilizan funciones conocidas y se corrobora que el algoritmo encuentre
	 * su optimo.
	 */
	public double getMinimoGlobal();

	/**
	 * Calcula el valor de la funcion en el punto especificado.
	 * @param valores coordenadas de entrada de la funcion
	 * @return valor de la funcion
	 */
	public double calcular(double[] valores);
}

