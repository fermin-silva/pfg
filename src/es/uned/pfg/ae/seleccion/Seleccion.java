package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.Poblacion;

public interface Seleccion {

	public Individuo[] seleccionar(Poblacion p, int cantidad);
}
