package es.uned.pfg.ae;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

import java.util.Arrays;

public class Individuo implements Comparable<Individuo> {

	public static final boolean USAR_JSON = false;
	
	protected double fitness;
	private double[] valores;
	
	private Funcion funcion;
	
	//TODO valores deberia ser un genotipo y el individuo tener el mapeo a fenotipo?
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
		return new Individuo(Arrays.copyOf(valores, valores.length), funcion);
	}
	
	@Override
	public int compareTo(Individuo o) {
		return Double.compare(o.fitness, fitness);
	}



	public static Individuo[] getIndividuosInicial(int tamaño, Funcion f) {
		return getIndividuosInicial(tamaño, f, Configuracion.ALEATORIO);
	}

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
