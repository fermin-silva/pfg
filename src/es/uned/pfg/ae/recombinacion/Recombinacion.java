package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;

public interface Recombinacion {

	public Individuo[] getCrias(Individuo... individuos);
}