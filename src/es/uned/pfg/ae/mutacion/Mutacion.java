package es.uned.pfg.ae.mutacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.utils.Aleatorio;

public interface Mutacion {

	public void mutar(Individuo i);

	public void set(Aleatorio aleatorio);
	public void setMin(double min);
	public void setMax(double max);
}
