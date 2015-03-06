package es.uned.pfg.ae.utils;

import java.util.Random;

import es.uned.pfg.ae.Individuo;

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
	
	public Individuo de(Individuo[] i) {
		return i[rnd.nextInt(i.length)];
	}
	
	public double de(double[] d) {
		return d[rnd.nextInt(d.length)];
	}
	
	public boolean menorQue(double probabilidad) {
		return get() < probabilidad;
	}
}
