package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * Toma un punto de recombinacion k. Luego, para el hijo 1, tomar los primeros
 * k numeros del padre 1 y ponerlos en el hijo. El resto es el promedio
 * aritmetico del padre 1 y 2. <br>
 * Hijo1 = { x<sub>1</sub> ... x<sub>k</sub>, a * y<sub>k+1</sub> +
 * 		   (1 - a) * x<sub>k+1</sub> ... a * y<sub>n</sub> +
 * 		   (1 - a) * x<sub>n</sub> } <br>
 * 
 * El hijo 2 es analogo, con x e y intercambiadas. 
 * <br>
 * En el libro: Simple Arithmetic Recombination (no confundir con SiNGle)
 * 
 * @author Fermin Silva
 */
public class RecombinacionSimple extends RecombinacionAlpha {

	private Aleatorio aleatorio;
	private Funcion funcion;
	
	private double min;
	private double max;


	public RecombinacionSimple() {
		
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] x = i1.getValores();
		double[] y = i2.getValores();
		
		int n = x.length;
		int k = aleatorio.getInt(n);
		
		double[] x2 = new double[n];
		double[] y2 = new double[n];
		
		//desde 0 hasta k se copian los valores del padre
		for (int i = 0; i <= k; i++) {
			x2[i] = x[i];
			y2[i] = y[i];
		}
		
		//desde k hasta n se utiliza el promedio aritmetico de los padres
		for (int i = k + 1; i < n; i++) {
			x2[i] = Utils.clamp(alpha * x[i] + (1 - alpha) * y[i], min, max);
			y2[i] = Utils.clamp(alpha * y[i] + (1 - alpha) * x[i], min, max);
		}

		return new Individuo[] { new Individuo(x2, funcion),
								 new Individuo(y2, funcion) };
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
	}
	
	@Override
	public void setFuncion(Funcion f) {
		this.funcion = f;
		this.min = f.getMin();
		this.max = f.getMax();
	}
	
	@Override
	public String toString() {
		return "RecombinacionSimple (" + alpha + ")"; 
	}
}
