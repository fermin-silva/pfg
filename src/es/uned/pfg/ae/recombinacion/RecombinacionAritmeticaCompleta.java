package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * Funciona tomando la suma ponderada de los alelos de cada padre.
 * Dados los vectores X e Y, el resultado es: <br>
 * 
 * Hijo1 = a * X + (1 - a) * Y <br>
 * Hijo2 = a * Y + (1 - a) * X <br>
 * <br>
 * Donde a es un valor entre [0, 1]. Si a = 1/2 los dos hijos serán identicos.
 * <br>
 * En el libro: Whole Arithmetic Recombination
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecombinacionAritmeticaCompleta extends RecombinacionAlpha {

	private Funcion funcion;
	
	private double min;
	private double max;


	//TODO y si se usa alpha = nextGaussian centrado en cero y con stdev de 1/3 ?
	public RecombinacionAritmeticaCompleta() {

	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] x = i1.getValores();
		double[] y = i2.getValores();
		
		double[] x1 = new double[x.length];
		double[] y1 = new double[y.length];
		
		for (int i = 0; i < x.length; i++) {
			x1[i] = alpha * x[i] + (1 - alpha) * y[i];
			y1[i] = alpha * y[i] + (1 - alpha) * x[i];
			
			x1[i] = Utils.clamp(x1[i], min, max);
			y1[i] = Utils.clamp(y1[i], min, max);
		}
		
		//TODO cambiarle la iteracion, no es cero!
		Individuo i11 = new Individuo(0, x1, funcion);
		Individuo i22 = new Individuo(0, y1, funcion);

		return new Individuo[] { i11, i22 };
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		//no hacer nada
	}
	
	public void setFuncion(Funcion f) {
		this.funcion = f;
		this.min = f.getMin();
		this.max = f.getMax();
	}
	
	@Override
	public String toString() {
		return "RecombinacionAritmeticaCompleta (" + alpha + ")"; 
	}
}
