package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Elige un alelo aleatorio k. En esa posicion se toma el promedio aritmetico
 * de los dos padres. El resto de los puntos se pasan sin modificaciones. <br>
 * 
 * Hijo1 = { x<sub>1</sub> ... x<sub>k - 1</sub>, a * y<sub>k</sub> +
 * 		   (1 - a) * x<sub>k</sub>, ... x<sub>n</sub> } <br><br>
 *
 * En el libro: Single Arithmetic Recombination
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecombinacionUnica extends RecombinacionK {

	public RecombinacionUnica(Aleatorio aleatorio, Funcion f) {
		this(aleatorio, f, 0.5);
	}
	
	public RecombinacionUnica(Aleatorio aleatorio, Funcion f, double alpha) {
		super(aleatorio, f, 1, alpha); //solo 1 punto de cruce
	}
}
