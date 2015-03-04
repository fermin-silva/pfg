package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;

public interface Seleccion {

	public Individuo[] seleccionar(Poblacion p, int cantidad);
}
