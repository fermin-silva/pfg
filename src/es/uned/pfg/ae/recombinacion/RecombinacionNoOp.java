package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecombinacionNoOp implements Recombinacion {

	@Override
	public Individuo[] getCrias(Individuo... padres) {
		return padres;
	}
}

