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
public class RecombinacionK extends RecombinacionAlpha {

	private Aleatorio aleatorio;
	private Funcion funcion;

	private double min;
	private double max;
	
	/** Cantidad de alelos que deben promediarse */
	private int k = 1;

		//Al poder ser alpha < 0, puede salir un poco del espacio interior de los padres!
		//TODO probar el < -0.1
//		alpha =  aleatorio.getEntre(-0.1, 0.4)); 
	
	public RecombinacionK() {
		
	}
	
	public RecombinacionK(int k) {
		this.k = k;
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
			
			//TODO agregar esta conclusion a la tesis
			//asi era originalmente, pero haciendo el algebra en el cuaderno me da al reves
			//en el libro se intercambia el gen de padre:
			//Ejemplo: se cambia el gen 2, el hijo X queda en -94 en vez de -247 (que esta mas cerca de -266)
			//	Padre X [-328.70, 172.33, -266.94 ]
			//	Padre Y [ 421.52,  28.67,  -75.83 ]
			// 	 Hijo X [-328.70, 172.33,  -94.94 ]
			// 	 Hijo Y [ 421.52,  28.67, -247.83 ]
			
			x2[i] = alpha * x[i] + (1 - alpha) * y[i];
			y2[i] = alpha * y[i] + (1 - alpha) * x[i];
			
			//Asi es como me da a mi en el cuaderno, el gen i que se cambia es del padre
			//correspondiente (x para x2 e y para y2)
//			x2[i] = alpha * y[i] + (1 - alpha) * x[i];
//			y2[i] = alpha * x[i] + (1 - alpha) * y[i];
			
			x2[i] = Utils.clamp(x2[i], min, max);
			y2[i] = Utils.clamp(y2[i], min, max);			
		}
		
		//TODO cambiarle la iteracion, no es cero!
		return new Individuo[] { new Individuo(0, x2, funcion), 
								 new Individuo(0, y2, funcion) };
	}

	@Override
	public String toString() {
		return "RecombinacionK (" + k + ", " + alpha + ")";
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
	}

	public void setK(int k) {
		this.k = k;
	}
	
	public void setFuncion(Funcion f) {
		this.funcion = f;
		this.min = f.getMin();
		this.max = f.getMax();
	}
}
