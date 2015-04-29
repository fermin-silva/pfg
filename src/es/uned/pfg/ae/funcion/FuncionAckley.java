package es.uned.pfg.ae.funcion;

import static java.lang.Math.*;

/**
 * <a href="http://www.sfu.ca/~ssurjano/ackley.html">
 * 		http://www.sfu.ca/~ssurjano/ackley.html
 * </a>
 *  
 * El minimo se encuentra en la coordenada 0 para cada xi. 
 *  
 * @author Fermin Silva < fermins@olx.com >
 */
public class FuncionAckley implements Funcion {

	private static final double MIN = -32.768;
	private static final double MAX = 32.768;

	private static final double A = 20;
	private static final double B = 0.2;
	private static final double C = 2 * Math.PI;
	
	private int d;

	
	public FuncionAckley() {
		
	}
	
	@Override
	public void setDimension(int dimension) {
		this.d = dimension;
	}
	
	@Override
	public int getDimension() {
		return d;
	}

	@Override
	public double getMin() {
		return MIN;
	}

	@Override
	public double getMax() {
		return MAX;
	}

	@Override
	public boolean isLimitada() {
		return true;
	}

	@Override
	public double calcular(double[] valores) {
		if (d <= 0) {
			throw new RuntimeException("Llamar a setDimension primero");
		}
		
		double sum1 = 0.0;
		double sum2 = 0.0;

		for (int i = 0; i < d; i++) {
			sum1 += valores[i] * valores[i];
			sum2 += cos(C * valores[i]);
		}

		return (-A * exp(-B * sqrt(sum1 / d)) - exp(sum2 / d) + A + E);
	}
	
	@Override
	public String toString() {
		return "F_Ackley(" + d + ")";
	}

	@Override
	public double getMinimoGlobal() {
		return 0;
	}
}

