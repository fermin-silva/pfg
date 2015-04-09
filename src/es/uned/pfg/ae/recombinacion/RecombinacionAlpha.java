package es.uned.pfg.ae.recombinacion;


/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public abstract class RecombinacionAlpha implements Recombinacion {

	protected double alpha = 0.5;

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
}

