package es.uned.pfg.ae.mutacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Interfaz generica que deben seguir todos los operadores de mutacion.
 *
 * @author Fermin Silva
 */
public interface Mutacion {

	/**
	 * Muta los genes del individuo. Para ello utiliza la probabilidad de
	 * mutacion por cada gen.
	 */
	public void mutar(Individuo i);

	/**
	 * Asigna un generador de numeros aleatorios especifico. Util para asignar
	 * uno con una semilla determinada y poder repetir experimentos.
	 */
	public void set(Aleatorio aleatorio);

	/**
	 * Asigna el valor minimo de las coordenadas de los individuos. Al mutar los
	 * genes, puede que uno de ellos termine valiendo menos que este minimo. En
	 * ese caso el valor debe restringirse.
	 */
	public void setMin(double min);

	/**
	 * Asigna el valor maximo de las coordenadas de los individuos. Al mutar los
	 * genes, puede que uno de ellos termine valiendo mas que este maximo. En
	 * ese caso el valor debe restringirse.
	 */
	public void setMax(double max);

	/**
	 * Asigna la probabilidad de mutar cada gen. Debe ser un numero entre [0, 1]
	 */
	public void setProb(double prob);
}
