package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

public interface Recombinacion {

	public Individuo[] getCrias(Individuo i1, Individuo i2);

	public void set(Aleatorio aleatorio);

	public void setFuncion(Funcion f);
}
