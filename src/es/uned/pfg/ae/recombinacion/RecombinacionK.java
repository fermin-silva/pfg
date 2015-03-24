package es.uned.pfg.ae.recombinacion;

import java.util.Arrays;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * Elige k alelos aleatorios. En esas posiciones se toma el promedio aritmetico
 * de los dos padres. El resto de los puntos se pasan sin modificaciones.
 * Es una extension a la Recombinacion Unica.
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecombinacionK implements Recombinacion {

	private Aleatorio aleatorio;
	private Funcion funcion;
	private double alpha;
	private double min;
	private double max;
	
	/** Cantidad de alelos que deben promediarse */
	private int k;

	public RecombinacionK(Aleatorio aleatorio, Funcion f, int k) {
		//Al poder ser alpha < 0, puede salir un poco del espacio interior de los padres!
		//TODO probar el < -0.1
		this(aleatorio, f, k, aleatorio.getEntre(-0.1, 0.4)); 
	}
	
	public RecombinacionK(Aleatorio aleatorio, Funcion f, int k, double alpha) {
		this.aleatorio = aleatorio;
		this.funcion = f;
		this.min = f.getMin();
		this.max = f.getMax();
		this.k = k;
		this.alpha = alpha;
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] x = i1.getValores();
		double[] y = i2.getValores();
		
		int n = x.length;
		
		//se copian los arrays enteros, luego se sobreescriben algunos alelos
		double[] x2 = Arrays.copyOf(x, n);
		double[] y2 = Arrays.copyOf(y, n);
		
		//sobreescribir k alelos:
		for (int cantidad = 0; cantidad < k; cantidad++) {
			int i = aleatorio.getInt(n); //punto de cruce
			
			//TODO probar si esta modificacion cambia algo
//			double min = Math.min(v1[i], v2[i]);
//			double max = Math.max(v1[i], v2[i]);
//			double delta = max - min;
//				
//			v11[i] = min + alpha * delta;
//			v22[i] = min + (1 - alpha) * delta;
			//end modificado
			
			x2[i] = alpha * x[i] + (1 - alpha) * y[i];
			y2[i] = alpha * y[i] + (1 - alpha) * x[i];
			
			x2[i] = Utils.clamp(x2[i], min, max);
			y2[i] = Utils.clamp(y2[i], min, max);			
		}
		
		//TODO cambiarle la iteracion, no es cero!
		return new Individuo[] { new Individuo(0, x2, funcion), 
								  new Individuo(0, y2, funcion) };
	}
}
