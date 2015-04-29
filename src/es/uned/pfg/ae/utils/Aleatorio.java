package es.uned.pfg.ae.utils;

import es.uned.pfg.ae.Individuo;

import java.util.Random;

public class Aleatorio {

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
//		System.err.println("Random get");
		return rnd.nextDouble();
	}
	
	/**
	 * Devuelve el siguiente número aleatorio siguiendo una distribución normal centrada
	 * en cero y con desviación típica de 1. No devolverá valores fuera del rango [-3, 3].
	 */
	public double getNormal() {
		return getNormal(0, 1);
	}
	
	public double getEntre(double a, double b) {
//		System.err.println("Random get entre");
		return get() * (b - a) + a;
	}
	
	/**
	 * Devuelve el siguiente número aleatorio siguiendo una distribución normal.
	 * Más del 99% de los valores estarán dentro de 3 desviaciones estándar, por lo que
	 * se limita los valores dentro de este rango para evitar números desproporcionalmente
	 * grandes.
	 */
	public double getNormal(double media, double stdev) {
//		System.err.println("Random get normal");
		
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
	
	public Individuo[] getDe(Individuo[] i, int cantidad) {
		Individuo[] ret = new Individuo[cantidad];
		
		for (int j = 0; j < cantidad; j++) {
			ret[j] = getDe(i);
		}
		
		return ret;
	}
	
	public Individuo getDe(Individuo[] i) {
		int index = rnd.nextInt(i.length); 
//		System.out.printf("   index %3d", index);
		
		return i[index];
	}
	
	public double getDe(double[] d) {
//		System.err.println("Random get de double");
		
		return d[rnd.nextInt(d.length)];
	}
	
	public boolean isMenorQue(double probabilidad) {
//		System.err.println("Random is menor que");
		
		return get() < probabilidad;
	}

	/**
	 * Devuelve una posicion aleatoria del array
	 */
	public int getPosicion(double[] d) {
//		System.err.println("Random get posicion");
		
		return rnd.nextInt(d.length);
	}
	
	/**
	 * Devuelve un numero entero aleatorio entre [0, n)
	 */
	public int getInt(int n) {
		return rnd.nextInt(n);
	}
	
	/**
	 * Devuelve una posicion aleatoria del array
	 */
	public int getPosicion(Object[] o) {
//		System.err.println("Random get posicion object");
		
		return rnd.nextInt(o.length);
	}
}
