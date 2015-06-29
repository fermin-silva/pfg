package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

import java.util.Arrays;

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
		for (int cantidad = 0; cantidad < Math.min(k, n); cantidad++) {
			int i = aleatorio.getInt(n); //punto de cruce (con reemplazamiento!)
			
			//asi es originalmente en el libro de Eiben
			x2[i] = alpha * x[i] + (1 - alpha) * y[i];
			y2[i] = alpha * y[i] + (1 - alpha) * x[i];
			
			//Asi es como me da a mi haciendo el algebra manualmente,
			// el gen i que se cambia es del padre correspondiente (x para x2 e y para y2)
//			x2[i] = alpha * y[i] + (1 - alpha) * x[i];
//			y2[i] = alpha * x[i] + (1 - alpha) * y[i];
			
			x2[i] = Utils.clamp(x2[i], min, max);
			y2[i] = Utils.clamp(y2[i], min, max);			
		}
		
		return new Individuo[] { new Individuo(x2, funcion),
								 new Individuo(y2, funcion) };
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
