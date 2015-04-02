package es.uned.pfg.ae.funcion;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class FuncionSchwefel implements Funcion {

	private static final double ALPHA = 418.9829;
	private static final double MIN = -500;
	private static final double MAX = 500;
	
	private int dimension;
	
	
	public FuncionSchwefel() {
		this(2);
	}
	
	public FuncionSchwefel(int dimension) {
		this.dimension = dimension;
	}
	
	@Override
	public double calcular(double[] valores) {
		double sum = 0;
		
		for (int i = 0; i < valores.length; i++) {
			sum += valores[i] * Math.sin(Math.sqrt(Math.abs(valores[i])));
		}
		
		return dimension * ALPHA - sum;
	}
	
	
	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public boolean isLimitada() {
		return true;
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
		return "F_Schwefel(" + dimension + ")";
	}
}