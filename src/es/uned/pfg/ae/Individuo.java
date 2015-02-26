package es.uned.pfg.ae;

import java.util.Arrays;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.StringUtils;

public class Individuo {

	private double fitness;
	private double[] valores;
	
	private Funcion funcion;
	
	private int generacion;
	
	
	public Individuo(int generacion, double[] valores, Funcion f) {
		this.generacion = generacion;
		this.valores = valores;
		this.funcion = f;
		
		calcularFitness();
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
		return StringUtils.toShortString(valores);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Individuo && 
			   Arrays.equals(valores, ((Individuo)obj).valores);
	}
}
