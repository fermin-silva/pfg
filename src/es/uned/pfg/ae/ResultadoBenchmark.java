package es.uned.pfg.ae;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.recombinacion.Recombinacion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Almacena los resultados de un benchmark para una funcion y recombinacion
 * concretas. Saca estadisticas como el minimo, maximo, promedio, desviacion,
 * etc asi como almacena las curvas de progreso y convergencia de las distintas
 * ejecuciones para luego promediarlas.
 *
 * @author Fermin Silva
 */
public class ResultadoBenchmark {

	private Individuo min, max;
	private double sumaFitness;
	private double sumaT;
	private int n;
	private List<List<Double>> progresos;
	private List<List<Double>> momentosInercia;
	private List<Double> fitness;

	private Recombinacion recombinacion;
	private Funcion funcion;


	public ResultadoBenchmark(Funcion f, Recombinacion recombinacion) {
		this.recombinacion = recombinacion;
		this.funcion = f;
		this.progresos = new LinkedList<List<Double>>();
		this.momentosInercia = new LinkedList<List<Double>>();
		this.fitness = new ArrayList<Double>();
	}

	/**
	 * Recoge los resultados de la ejecucion del AG
	 */
	public void recolectar(AlgoritmoGenetico ag) {
		progresos.add(ag.getCurvaProgreso());
		momentosInercia.add(ag.getCurvaCovergencia());

		Individuo individuo = ag.getMejorIndividuo();

		if (min == null || individuo.getFitness() < min.getFitness()) {
			min = individuo;
		}

		if (max == null || individuo.getFitness() > max.getFitness()) {
			max = individuo;
		}

		fitness.add(individuo.getFitness());

		sumaFitness += individuo.getFitness();
		sumaT += ag.getTiempo();
		n++;
	}

	/**
	 * Obtiene la desviacion tipica o estandar de la fitness entre todas las
	 * ejecuciones del algoritmo
	 */
	public double getStdev() {
		double avgFitness = sumaFitness / n;
		double sum = 0;
		
		for (Double val : fitness) {
			sum += Math.pow(avgFitness - val, 2);
		}
		
		return Math.sqrt(sum / n);
	}

	public List<List<Double>> getMomentosInercia() { return momentosInercia; }

	public List<List<Double>> getProgresos() {
		return progresos;
	}

	public List<Double> getFitness() {
		return fitness;
	}

	public Recombinacion getRecombinacion() {
		return recombinacion;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public double getTiempo() {
		return sumaT / n / 1000;
	}

	@Override
	public String toString() {
		double avgFitness = sumaFitness / n;
		double avgT = sumaT / n / 1000;
		double stdev = getStdev();

		return String.format("%s\t%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.2fs",
							recombinacion.toString(), funcion.toString(),
							min.getFitness(), avgFitness, max.getFitness(),
							stdev, avgT);
	}
}

