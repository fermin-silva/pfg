package es.uned.pfg.ae.funcion;

import static java.lang.Math.pow;
import static java.lang.Math.sin;

/**
 * <a href="http://www.sfu.ca/~ssurjano/schaffer2.html">
 * 		http://www.sfu.ca/~ssurjano/schaffer2.html
 * </a>
 * 
 * El minimo se encuentra en la coordenada (0, 0).
 * 
 * @author Fermin Silva
 */
public class FuncionSchaffer2 implements Funcion {
	
	private static final double MIN = -50;
	private static final double MAX = 50;

	public FuncionSchaffer2() {
		
	}
	
	@Override
	public void setDimension(int dimension) {
		//solo es valido dimension 2, no hacer nada
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
	public double calcular(double[] valores) {
		double x12 = valores[0] * valores[0];
		double x22 = valores[1] * valores[1];
		
		return 0.5 + (pow(sin(x12 - x22), 2) - 0.5) /
					   pow(1 + 0.001 * (x12 + x22), 2);
	}
	
	@Override
	public String toString() {
		return "F_Schaffer2";
	}
	
	@Override
	public double getMinimoGlobal() {
		return 0;
	}
}

