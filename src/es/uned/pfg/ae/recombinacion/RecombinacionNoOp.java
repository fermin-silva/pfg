package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Recombinacion de No Operacion (NoOp), que no realiza nada.
 *
 * @author Fermin Silva
 */
public class RecombinacionNoOp implements Recombinacion {

	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		return null; //devolver nulo es equivalente a no recombinar los padres
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		//no hacer nada
	}
	
	public void setFuncion(Funcion f) {
		//no hacer nada
	}
	
	@Override
	public String toString() {
		return "RecombinacionNO_OP";
	}
}

