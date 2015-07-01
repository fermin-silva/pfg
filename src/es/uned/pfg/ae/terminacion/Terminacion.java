package es.uned.pfg.ae.terminacion;

import es.uned.pfg.ae.poblacion.Poblacion;

/**
 * Interfaz generica que deben seguir todos los criterios de terminacion.
 *
 * @author Fermin Silva
 */
public interface Terminacion {

	/**
	 * Determina si el algoritmo debe finalizar en la generacion actual.
	 * Se deja a la implementacion el criterio particular, como cantidad fija
	 * de generaciones, convergencia de la poblacion, etc.
	 * Si devuelve verdadero, indica que el algoritmo debe finalizar, de lo
	 * contrario se seguira iterando.
	 */
	public boolean isTerminado(int iteracion, Poblacion p);
}
