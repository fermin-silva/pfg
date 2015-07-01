package es.uned.pfg.ae.mutacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 *  Mutacion No Operacion (NoOp), que no realiza nada.
 *
 *	@author Fermin Silva
 */
public class MutacionNoOp implements Mutacion {

	@Override
	public void mutar(Individuo i) {
		//no hacer nada
	}

	@Override
	public void set(Aleatorio aleatorio) {
		//no hacer nada
	}

	@Override
	public void setMin(double min) {
		//no hacer nada
	}

	@Override
	public void setMax(double max) {
		//no hacer nada
	}

	@Override
	public void setProb(double prob) {
		//no hacer nada
	}

	@Override
	public String toString() {
		return "MutacionNoOp";
	}
}