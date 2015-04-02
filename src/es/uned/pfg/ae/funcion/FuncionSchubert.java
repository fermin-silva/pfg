package es.uned.pfg.ae.funcion;

import static java.lang.Math.*;

/**
 * <a href="http://www.sfu.ca/~ssurjano/shubert.html">
 * 		http://www.sfu.ca/~ssurjano/shubert.html
 * </a>
 *  
 * El minimo global vale -186.7309 
 *  
 * @author Fermin Silva < fermins@olx.com >
 */
public class FuncionSchubert implements Funcion {

	private static final double MIN = -10;
	private static final double MAX = 10;

	public FuncionSchubert() {
		
	}
	
	@Override
	public double getMinimoGlobal() {
		return -186.7309;
	}
	
	@Override
	public int getDimension() {
		return 2;
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
		double x1 = valores[0];
		double x2 = valores[1];
		
		double sum1 = 0;
		double sum2 = 0;
		
		for (int i = 1; i <= 5; i++) {
			sum1 += i * cos((i + 1) * x1 + i);
			sum2 += i * cos((i + 1) * x2 + i);
		}
		
		return sum1 * sum2;
	}
	
	@Override
	public String toString() {
		return "F_Schubert";
	}
}

