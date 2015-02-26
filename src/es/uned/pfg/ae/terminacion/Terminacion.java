package es.uned.pfg.ae.terminacion;

import es.uned.pfg.ae.Poblacion;

public interface Terminacion {

	public boolean isTerminado(int iteracion, Poblacion p);
}
