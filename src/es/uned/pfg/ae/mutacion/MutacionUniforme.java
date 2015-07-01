package es.uned.pfg.ae.mutacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Mutacion que genera un valor aleatorio uniforme entre todo el dominio de la
 * funcion.
 * Tiene como desventaja que puede generar mutaciones muy grandes.
 *
 * @author Fermin Silva
 */
public class MutacionUniforme implements Mutacion {

	private double max;
	private double min;
	private double prob;
	private Aleatorio aleatorio;
	
	public MutacionUniforme() {
		
	}
	
	@Override
	public void mutar(Individuo i) {
		double[] alelos = i.getValores();
		
		boolean cambiado = false;
		
		for (int j = 0; j < alelos.length; j++) {
			if (aleatorio.isMenorQue(prob)) {
				alelos[j] = aleatorio.getEntre(min, max);
				cambiado = true;
			}
		}
		
		if (cambiado) {
			i.calcularFitness();
		}
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
	}
	
	public void setMin(double min) {
		this.min = min;
	}
	
	public void setMax(double max) {
		this.max = max;
	}

	@Override
	public void setProb(double prob) {
		this.prob = prob;
	}
	
	@Override
	public String toString() {
		return "MutacionUniforme (prob " + prob + ")";
	}
}
