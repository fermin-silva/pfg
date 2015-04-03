package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class SeleccionNoOp implements Seleccion {

	@Override
	public Individuo[] seleccionar(Poblacion p) {
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

