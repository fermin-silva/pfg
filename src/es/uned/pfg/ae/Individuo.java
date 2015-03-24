package es.uned.pfg.ae;

import java.util.Arrays;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Utils;

public class Individuo implements Comparable<Individuo> {

	public static final boolean USAR_JSON = false;
	
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
		//TODO si es un problema de maximizacion, +funcion, sino -funcion
		this.fitness = -funcion.calcular(valores);
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
		if (USAR_JSON) {
			return "{ \"fit\" : " + Utils.toString(getFitness()) + 
					", \"vals\" : " + Utils.toShortString(valores) + " }";
		}
		else {
			return Utils.toShortString(valores) + " == " + Utils.toString(getFitness());
//			return Utils.toString(getFitness()) + 
//					" ==> " + toShortString(valores);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Individuo && 
			   Arrays.equals(valores, ((Individuo)obj).valores);
	}


	public Individuo clone() {
		return new Individuo(generacion, Arrays.copyOf(valores, valores.length), funcion);
	}
	
	@Override
	public int compareTo(Individuo o) {
		return Double.compare(o.fitness, fitness);
	}
}
