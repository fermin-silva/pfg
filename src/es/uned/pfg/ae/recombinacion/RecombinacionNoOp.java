package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecombinacionNoOp implements Recombinacion {

	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		return new Individuo[] { i1, i2 };
	}
}

