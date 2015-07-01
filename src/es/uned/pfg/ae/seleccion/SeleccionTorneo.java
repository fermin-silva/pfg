package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Seleccion por torneo como figura en el libro de Eiben.
 * El tamaño del torneo es parametrizable.
 *
 * @author Fermin Silva
 */
public class SeleccionTorneo implements Seleccion {

	private int tamaño;
	private Aleatorio aleatorio;

	
	public SeleccionTorneo() {
		
	}
	
	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}
	
	@Override
	public Individuo[] seleccionar(Poblacion p) {
		Individuo[] individuos = p.getIndividuos();
		Individuo[] pool = new Individuo[individuos.length];
		
		for (int i = 0; i < individuos.length; i++) {
			//crea un torneo de n invidivuos
			Individuo[] torneo = aleatorio.getDe(individuos, tamaño);

			//asigna al mating pool al ganador del torneo
			Individuo ganador = p.getMejorIndividuo(torneo);
			pool[i] = ganador;
		}
		
		return pool;
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
	}
	
	@Override
	public String toString() {
		return "SeleccionTorneo (" + tamaño + ")";
	}
}
