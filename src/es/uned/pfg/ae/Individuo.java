package es.uned.pfg.ae;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

import java.util.Arrays;

/**
 * Individuo de la poblacion sujeto a la seleccion natural.
 * Contiene una serie de valores geneticos (su genotipo), que lo hacen mas
 * o menos apto. Almacenan su valor de aptitud de supervivencia en la variable
 * fitness.
 *
 * @author Fermin Silva
 */
public class Individuo implements Comparable<Individuo> {

	protected double fitness;
	private double[] valores;
	
	private Funcion funcion;
	
	public Individuo(double[] valores, Funcion f) {
		this.valores = valores;
		this.funcion = f;
		
		calcularFitness();
	}
	
	protected Individuo() {
		
	}

	public double getValorFuncion() {
		return funcion.calcular(valores);
	}

	/**
	 * Calcula el valor de aptitud del individuo segun sus genes.
	 * El valor de la fitness es tanto mayor cuanto mejor sea la aptitud del
	 * individuo.
	 */
	public void calcularFitness() {
		//TODO si es un problema de maximizacion, +funcion, sino -funcion
		this.fitness = funcion.getMinimoGlobal() - funcion.calcular(valores);
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public Funcion getFuncion() {
		return funcion;
	}
	
	public double[] getValores() {
		return valores;
	}

	public void setValores(double[] valores) {
		this.valores = valores;
	}
	
	
	@Override
	public String toString() {
			return Utils.toShortString(valores) + " == " + Utils.toString(getFitness());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Individuo && 
			   Arrays.equals(valores, ((Individuo)obj).valores);
	}

	/**
	 * Genera un individuo identico, un clon.
	 */
	public Individuo clone() {
		return new Individuo(Arrays.copyOf(valores, valores.length), funcion);
	}
	
	@Override
	public int compareTo(Individuo o) {
		return Double.compare(o.fitness, fitness);
	}


	/**
	 * Genera una poblacion aleatoria de individuos
	 */
	public static Individuo[] getIndividuosInicial(int tamaño, Funcion f) {
		return getIndividuosInicial(tamaño, f, Configuracion.ALEATORIO);
	}

	/**
	 * Genera una poblacion aleatoria de individuos utilizando un determinado
	 * generador de numeros aleatorios.
	 */
	public static Individuo[] getIndividuosInicial(int tamaño, Funcion f,
												   Aleatorio aleatorio) {
		int dimension = f.getDimension();

		Individuo[] individuos = new Individuo[tamaño];

		for (int i = 0; i < individuos.length; i++) {
			double[] valores = new double[dimension];

			for (int j = 0; j < valores.length; j++) {
				valores[j] = aleatorio.getEntre(f.getMin(), f.getMax());
			}

			individuos[i] = new Individuo(valores, f);
		}

		return individuos;
	}
}
