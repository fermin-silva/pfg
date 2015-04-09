package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;

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
			Individuo[] torneo = aleatorio.getDe(individuos, tamaño);
			Individuo ganador = p.getMejorIndividuo(torneo);
			
//			System.out.println(" ==> " + Arrays.toString(torneo) + " --- Gano : " + Utils.toString(ganador.getFitness()));
			
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
