package es.uned.pfg.ae;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.recombinacion.Recombinacion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
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

	public void recolectar(AlgoritmoGenetico ag) {
		progresos.add(ag.getCurvaProgreso());
		momentosInercia.add(ag.getMomentosInercia());

		Individuo individuo = ag.getMejorIndividuo();

		if (min == null || individuo.getFitness() < min.getFitness()) {
			min = individuo;
		}
		else if (max == null || individuo.getFitness() > max.getFitness()) {
			max = individuo;
		}

		fitness.add(individuo.getFitness());

		sumaFitness += individuo.getFitness();
		sumaT += ag.getTiempo();
		n++;
	}
	
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

