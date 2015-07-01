package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 *  Seleccion No Operacion (NoOp), que no realiza nada.
 *
 *	@author Fermin Silva
 */
public class SeleccionNoOp implements Seleccion {

	@Override
	public Individuo[] seleccionar(Poblacion p) {
		//devuelve la misma poblacion sin cambios ni selecciones
		return p.getIndividuos();
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		//no hacer nada
	}
	
	@Override
	public String toString() {
		return "SeleccionNoOp";
	}
}

