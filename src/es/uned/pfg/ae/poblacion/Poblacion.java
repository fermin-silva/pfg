package es.uned.pfg.ae.poblacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.utils.Utils;

//TODO crear poblacion generacional y steady-state como dos implementaciones distintas
public abstract class Poblacion {

	protected Individuo[] individuos;

	private Individuo mejorIndividuo;
	
	
	public Poblacion(Individuo[] individuos) {
		this.individuos = individuos;
		this.mejorIndividuo = getMejorIndividuo(individuos);
	}

	/**
	 * Calcula los supervivientes de la siguiente generación y los integra a la población.
	 * 
	 * @param crias Los nuevos individuos generados en la recombinación que deben ser
	 * integrados a la población.
	 */
	public abstract void setSobrevivientes(Individuo[] crias);

	public Individuo getMejorIndividuo() {
		return mejorIndividuo;
	}

	public void setMejorIndividuo(Individuo mejorIndividuo) {
		this.mejorIndividuo = mejorIndividuo;
	}
	
	public Individuo getMejorIndividuo(Individuo[] individuos) {
		Individuo mejor = individuos[0];
		
		for (int i = 1; i < individuos.length; i++) {

			//la comparacion es < 0 tanto si el problema es de maximizacion
			//como de minimizacion. La logica de cambiar el signo de la 
			//comparacion se hace en el propio codigo de Individuo
			if (individuos[i].compareTo(mejor) < 0) {
				mejor = individuos[i];
			}
		}
		
		return mejor;
	}
	
	public int encontrarPeor(Individuo[] individuos) {
		Individuo peor = individuos[0];
		int indice = 0;
		
		for (int i = 1; i < individuos.length; i++) {

			//la comparacion es > 0 tanto si el problema es de maximizacion
			//como de minimizacion. La logica de cambiar el signo de la 
			//comparacion se hace en el propio codigo de Individuo
			if (individuos[i].compareTo(peor) > 0) {
				peor = individuos[i];
				indice = i;
			}
		}
		
		return indice;
	}
	
	public Individuo[] getIndividuos() {
		return individuos;
	}
	
	@Override
	public String toString() {
		return Utils.toString(individuos);
	}
}
