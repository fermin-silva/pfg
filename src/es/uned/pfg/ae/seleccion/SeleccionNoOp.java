package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class SeleccionNoOp implements Seleccion {

	@Override
	public Individuo[] seleccionar(Poblacion p) {
		return p.getIndividuos();
	}
}

