package es.uned.pfg.ae.seleccion;

import java.util.Arrays;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;

public class SeleccionTorneo implements Seleccion {

	private int tamaño;
	private Aleatorio aleatorio;

	
	public SeleccionTorneo(int tamaño, Aleatorio aleatorio) {
		this.tamaño = tamaño;
		this.aleatorio = aleatorio;
	}
	
	@Override
	public Individuo[] seleccionar(Poblacion p) {
		Individuo[] individuos = p.getIndividuos();
		Individuo[] pool = new Individuo[individuos.length];
		
		for (int i = 0; i < individuos.length; i++) {
			Individuo[] torneo = aleatorio.getDe(individuos, tamaño);
			
//			System.out.println("Elegi aleatoriamente a " + Arrays.toString(torneo));
			
			pool[i] = p.getMejorIndividuo(torneo);
		}
		
		return pool;
	}
}
