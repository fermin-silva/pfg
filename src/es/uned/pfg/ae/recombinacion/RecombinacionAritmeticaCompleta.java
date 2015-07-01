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
 * @author Fermin Silva
 */
public class RecombinacionAritmeticaCompleta extends RecombinacionAlpha {

	private Funcion funcion;
	
	private double min;
	private double max;


	public RecombinacionAritmeticaCompleta() {

	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] x = i1.getValores();
		double[] y = i2.getValores();
		
		double[] x2 = new double[x.length];
		double[] y2 = new double[y.length];
		
		for (int i = 0; i < x.length; i++) {
			x2[i] = alpha * x[i] + (1 - alpha) * y[i];
			y2[i] = alpha * y[i] + (1 - alpha) * x[i];
			
			x2[i] = Utils.clamp(x2[i], min, max);
			y2[i] = Utils.clamp(y2[i], min, max);
		}

		return new Individuo[] { new Individuo(x2, funcion),
								 new Individuo(y2, funcion) };
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		//no hacer nada, ya que no utiliza numeros aleatorios
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
