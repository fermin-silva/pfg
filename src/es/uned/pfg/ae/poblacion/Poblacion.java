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
			if (individuos[i].getFitness() > mejor.getFitness()) {
				mejor = individuos[i];
			}
		}
		
		return mejor;
	}
	
	public int encontrarPeor(Individuo[] individuos) {
		Individuo peor = individuos[0];
		int indice = 0;
		
		for (int i = 1; i < individuos.length; i++) {
			if (individuos[i].getFitness() < peor.getFitness()) {
				peor = individuos[i];
				indice = i;
			}
		}
		
		return indice;
	}

	public double getMomentoInercia() {
		double[] centroide = getCentroide();

		int n = mejorIndividuo.getFuncion().getDimension();
		double sum = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < individuos.length; j++) {
				double[] x = individuos[j].getValores();

				sum += Math.pow(x[i] - centroide[i], 2);
			}
		}

		//TODO diferencias entre devolver
		return sum / individuos.length;
	}

	public double[] getCentroide() {
		int d = mejorIndividuo.getFuncion().getDimension();
		double[] centroide = new double[d];

		for (Individuo individuo : individuos) {
			double[] valores = individuo.getValores();

			for (int i = 0; i < valores.length; i++) {
				centroide[i] += valores[i];
			}
		}

		for (int i = 0; i < centroide.length; i++) {
			centroide[i] /= (float)individuos.length;
		}

		return centroide;
	}

	public Individuo[] getIndividuos() {
		return individuos;
	}
	
	public int getTamaño() {
		return individuos.length;
	}
	
	@Override
	public String toString() {
		return Utils.toString(individuos);
	}
}
