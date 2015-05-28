package es.uned.pfg.ae.funcion;

/**
 * <a href="http://www.sfu.ca/~ssurjano/schwef.html">
 * 		http://www.sfu.ca/~ssurjano/schwef.html
 * </a>
 * 
 * El minimo se encuentra en la coordenada 420.9687 para cada xi.
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class FuncionSchwefel implements Funcion {

	private static final double ALPHA = 418.9829;
	private static final double MIN = -500;
	private static final double MAX = 500;
	
	private int d;
	
	
	public FuncionSchwefel() {
		
	}
	
	public void setDimension(int dimension) {
		this.d = dimension;
	}
	
	@Override
	public double calcular(double[] valores) {
		if (d <= 0) {
			throw new RuntimeException("Llamar a setDimension primero");
		}
		
		double sum = 0;
		
		for (int i = 0; i < d; i++) {
			sum += valores[i] * Math.sin(Math.sqrt(Math.abs(valores[i])));
		}
		
		return d * ALPHA - sum;
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
	public String toString() {
		return "F_Schwefel(" + d + ")";
	}
	
	@Override
	public double getMinimoGlobal() {
		return 0;
	}
}