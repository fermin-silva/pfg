package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Interfaz generica que deben seguir todos los operadores de recombinacion.
 *
 * @author Fermin Silva
 */
public interface Recombinacion {

	/**
	 * Dados dos individuos, genera un arreglo con las dos crias. Este arreglo
	 * puede ser nulo si los individuos no se recombinan por algun motivo.
	 * Si no es nulo, el arreglo debe contener exactamente 2 individuos.
	 */
	public Individuo[] getCrias(Individuo i1, Individuo i2);

	/**
	 * Asigna un generador de numeros aleatorios especifico. Util para asignar
	 * uno con una semilla determinada y poder repetir experimentos.
	 */
	public void set(Aleatorio aleatorio);

	/**
	 * Asigna la funcion que se esta optimizando. Cuando se generan los hijos
	 * luego de la recombinacion, hace falta asignarles la funcion original,
	 * por lo que sera necesario almacenarla.
	 */
	public void setFuncion(Funcion f);
}
