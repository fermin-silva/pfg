package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;

public interface Seleccion {

	public Individuo[] seleccionar(Poblacion p);

	public void set(Aleatorio aleatorio);
}
