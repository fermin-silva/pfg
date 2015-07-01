package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Interfaz generica que deben seguir todos los operadores de seleccion.
 *
 * @author Fermin Silva
 */
public interface Seleccion {

	/**
	 * Dada una poblacion, retorna un arreglo con los individuos seleccionados
	 * para reproducirse (mating pool). El tamaño de este arreglo es el mismo
	 * que el tamaño de la poblacion. Esto permitira distintos modelos
	 * poblacionales (steady-state, generacional, etc).
	 */
	public Individuo[] seleccionar(Poblacion p);

	/**
	 * Asigna un generador de numeros aleatorios especifico. Util para asignar
	 * uno con una semilla determinada y poder repetir experimentos.
	 */
	public void set(Aleatorio aleatorio);
}
