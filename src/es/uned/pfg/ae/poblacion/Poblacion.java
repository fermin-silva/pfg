package es.uned.pfg.ae.poblacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.utils.Utils;

/**
 * Clase abstracta que modela los metodos basicos de un modelo poblacional.
 *
 * @author Fermin Silva
 */
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

	/**
	 * Obtiene el mejor individuo del arreglo recibido por parametro. Debe
	 * ser invocado cada vez que se cambian los individuos de la poblacion.
	 */
	public Individuo getMejorIndividuo(Individuo[] individuos) {
		Individuo mejor = individuos[0];
		
		for (int i = 1; i < individuos.length; i++) {
			if (individuos[i].getFitness() > mejor.getFitness()) {
				mejor = individuos[i];
			}
		}
		
		return mejor;
	}

	/**
	 * Encuentra el peor individuo del arreglo de individuos recibido por
	 * parametro. Util cuando se desea reemplazar un individuo por el peor de
	 * todos.
	 */
	protected int encontrarPeor(Individuo[] individuos) {
		Individuo peor = individuos[0];
		int indice = 0;
		
		for (int i = 1; i < individuos.length; i++) {
			// aqui se evidencia que los AGs maximizan la fitness,
			// independientemente si se trata de un problema de minimizacion
			// o no. Un individuo es peor que otro si su fitness es menor
			if (individuos[i].getFitness() < peor.getFitness()) {
				peor = individuos[i];
				indice = i;
			}
		}
		
		return indice;
	}

	/**
	 * Devuelve la desviacion de la poblacion con respecto a su centroide.
	 * Su calculo es similar al de la desviacion tipica
	 */
	public double getDesviacion() {
		double[] centroide = getCentroide();

		int n = mejorIndividuo.getFuncion().getDimension();
		double sum = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < individuos.length; j++) {
				double[] x = individuos[j].getValores();

				sum += Math.pow(x[i] - centroide[i], 2);
			}
		}

		return Math.sqrt(sum / individuos.length);
	}

	/**
	 * Calcula el centroide de la poblacion. Para ello calcula, para cada
	 * coordenada (posicion dentro del arreglo de genes), su promedio entre
	 * toda la poblacion. El resultado es un arreglo con los todos promedios.
	 */
	public double[] getCentroide() {
		int d = mejorIndividuo.getFuncion().getDimension();
		double[] centroide = new double[d];

		for (Individuo individuo : individuos) {
			double[] valores = individuo.getValores();

			for (int i = 0; i < valores.length; i++) {
				//primero se suman los valores de todos los individuos para la
				//coordenada i-esima
				centroide[i] += valores[i];
			}
		}

		for (int i = 0; i < centroide.length; i++) {
			//luego se dividen por n, para calcular el promedio
			centroide[i] /= (float)individuos.length;
		}

		return centroide;
	}

	/**
	 * Devuelve los individuos de la poblacion en la generacion actual
	 */
	public Individuo[] getIndividuos() {
		return individuos;
	}
	
	@Override
	public String toString() {
		return Utils.toString(individuos);
	}
}
