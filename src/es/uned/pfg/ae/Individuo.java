package es.uned.pfg.ae;

import static es.uned.pfg.ae.utils.StringUtils.toShortString;

import java.util.Arrays;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.StringUtils;

public class Individuo implements Comparable<Individuo> {

	//TODO parametrizar esta constante
	/**
	 * True si el problema es de maximizacion: un individuo es mejor si su
	 * fitness es mayor.
	 * False si el problema es de minimizacion: un individuo es mejor si su
	 * fitness es menor.
	 */
	public static final boolean MAXIMIZAR = false;
	
	protected double fitness;
	private double[] valores;
	
	private Funcion funcion;
	
	private int generacion;
	
	//TODO valores deberia ser un genotipo y el individuo tener el mapeo a fenotipo?
	public Individuo(int generacion, double[] valores, Funcion f) {
		this.generacion = generacion;
		this.valores = valores;
		this.funcion = f;
		
		calcularFitness();
	}
	
	protected Individuo() {
		
	}
	
	public void calcularFitness() {
		this.fitness = funcion.calcular(valores);
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public double[] getValores() {
		return valores;
	}

	public int getGeneracion() {
		return generacion;
	}
	
	public void setValores(double[] valores) {
		this.valores = valores;
	}
	
	
	@Override
	public String toString() {
		return "{ \"fit\" : " + StringUtils.toString(getFitness()) + 
				", \"vals\" : " + toShortString(valores) + " }";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Individuo && 
			   Arrays.equals(valores, ((Individuo)obj).valores);
	}


	@Override
	public int compareTo(Individuo o) {
		if (MAXIMIZAR) {
			return Double.compare(o.fitness, fitness);
		}
		else { //se invierte la comparacion para invertir el signo de la misma
			return Double.compare(fitness, o.fitness);
		}
	}
}
