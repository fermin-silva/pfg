package es.uned.pfg.ae;


public abstract class Poblacion {

	private Individuo[] individuos;

	
	public Poblacion(Individuo[] individuos) {
		this.individuos = individuos;
	}

	/**
	 * Devuelve la fitness de la población, dependiendo de la implementación puede ser
	 * del mejor individuo actual, o del mejor histórico.
	 * 
	 * @return El valor de la función de adecuamiento o fitness
	 */
	public abstract double getFitness();
	
	/**
	 * Calcula los supervivientes de la siguiente generación y los integra a la población.
	 * 
	 * @param padres Los padres de la generación anterior, utilizados dependiendo de la
	 * implementación de la población
	 * @param crias Los nuevos individuos generados en la recombinación que deben ser
	 * integrados a la población.
	 */
	public abstract void setSobrevivientes(Individuo[] padres, Individuo[] crias);

	/**
	 * Dependiendo de la implementación puede ser el mejor individuo actual, o el mejor 
	 * de toda la historia.
	 *  
	 * @return El mejor individuo de la población.
	 */
	public abstract Individuo getMejorIndividuo();
	
	
	public Individuo[] getIndividuos() {
		return individuos;
	}
}
