package es.uned.pfg.ae.funcion;

import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

/**
 * <a href="http://www.sfu.ca/~ssurjano/griewank.html">
 * 		http://www.sfu.ca/~ssurjano/griewank.html
 * </a>
 *  
 * El minimo se encuentra en la coordenada 0 para cada xi. 
 *  
 * @author Fermin Silva < fermins@olx.com >
 */
public class FuncionGriewank implements Funcion {

	private static final double MIN = -600;
	private static final double MAX = 600;

	private int d;

	public FuncionGriewank(int dimension) {
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
		double prod = 1;
		
		for (int i = 1; i < d; i++) {
			double x = valores[i - 1];
			
			sum += x * x;
			prod *= cos(x / sqrt(i));
		}
		
		return sum / 4000 - prod + 1;
	}
	
	@Override
	public String toString() {
		return "F_Griewank(" + d + ")";
	}

	@Override
	public double getMinimoGlobal() {
		return 0;
	}
}

