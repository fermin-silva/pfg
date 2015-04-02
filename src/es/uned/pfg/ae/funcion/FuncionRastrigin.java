package es.uned.pfg.ae.funcion;

import static java.lang.Math.*;

/**
 * <a href="http://www.sfu.ca/~ssurjano/rastr.html">
 * 		http://www.sfu.ca/~ssurjano/rastr.html
 * </a>
 * 
 * El minimo se encuentra en la coordenada 0 para cada xi.
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class FuncionRastrigin implements Funcion {

	private static final double MIN = -5.12;
	private static final double MAX = 5.12;
	
	private int d;
	
	public FuncionRastrigin(int dimension) {
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
		double sum = 0;
		
		for (int i = 0; i < valores.length; i++) {
			sum += (valores[i] * valores[i] + -10 * cos(2 * PI * valores[i]));
		}
		
		return 10 * d + sum;
	}
	
	@Override
	public String toString() {
		return "F_Rastrigin(" + d + ")";
	}
	
	@Override
	public double getMinimoGlobal() {
		return 0;
	}
}

