package es.uned.pfg.ae.utils;

import es.uned.pfg.ae.Individuo;

import java.util.Random;

/**
 * Clase utilitaria que otorga funcionalidades de alto nivel al generador de
 * numeros aleatorios.
 *
 * @author Fermin Silva
 */
public class Aleatorio {

	/** Generador interno de numeros aleatorios */
	private Random rnd;

	public Aleatorio() {
		this(System.currentTimeMillis());
	}
	
	public Aleatorio(long semilla) {
		this.rnd = new Random(semilla);
	}
	
	/**
	 * Devuelve el siguiente número aleatorio entre 0 y 1, distribuido uniformemente en
	 * todo el rango de valores.
	 */
	public double get() {
		return rnd.nextDouble();
	}
	
	/**
	 * Devuelve el siguiente número aleatorio siguiendo una distribución normal centrada
	 * en cero y con desviación típica de 1. No devolverá valores fuera del rango [-3, 3].
	 */
	public double getNormal() {
		return getNormal(0, 1);
	}

	/**
	 * Devuelve el siguiente numero aleatorio distribuido uniformemente entre
	 * dos valores especificados.
	 */
	public double getEntre(double a, double b) {
		return get() * (b - a) + a;
	}
	
	/**
	 * Devuelve el siguiente número aleatorio siguiendo una distribución normal.
	 * Más del 99% de los valores estarán dentro de 3 desviaciones estándar, por lo que
	 * se limita los valores dentro de este rango para evitar números desproporcionalmente
	 * grandes.
	 */
	public double getNormal(double media, double stdev) {
		double val = rnd.nextGaussian() * stdev;
		
		double limite = 3 * stdev;
		
		if (val > limite) {
			val = limite;
		}
		
		if (val < -limite) {
			val = -limite;
		}
		
		return val + media;
	}

	/**
	 * Devuelve n individuos aleatorios de un arreglo con reemplazamiento y
	 * en orden aleatorio.
	 */
	public Individuo[] getDe(Individuo[] i, int cantidad) {
		Individuo[] ret = new Individuo[cantidad];
		
		for (int j = 0; j < cantidad; j++) {
			ret[j] = getDe(i);
		}
		
		return ret;
	}

	/** devuelve un individuo aleatorio del arreglo */
	public Individuo getDe(Individuo[] i) {
		int index = rnd.nextInt(i.length); 

		return i[index];
	}

	/**
	 * Genera un numero aleatorio y lo compara con el valor de la probabilidad
	 * (entre 0 y 1). Devuelve verdadero si este ultimo es menor que el numero
	 * generado. <br>
	 * Util para sentencias del tipo 'hacer algo con probabilidad de x %'.
	 * Para ello simplemente hacer if (isMenorQue(probabilidad)) hacer_algo
	 */
	public boolean isMenorQue(double probabilidad) {
		return get() < probabilidad;
	}

	/**
	 * Devuelve un numero entero aleatorio entre [0, n)
	 */
	public int getInt(int n) {
		return rnd.nextInt(n);
	}
}
