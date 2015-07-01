package es.uned.pfg.ae.recombinacion;


/**
 * Clase abstracta cuyo unico proposito es almacenar el valor de alfa para
 * todas las recombinaciones aritmeticas.
 *
 * @author Fermin Silva
 */
public abstract class RecombinacionAlpha implements Recombinacion {

	protected double alpha = 0.5;

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
}

