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
public class RecombinacionAritmeticaCompleta implements Recombinacion {

	private Funcion funcion;
	private double alpha;
	private double probabilidad;
	
	private Aleatorio aleatorio;
	private double min;
	private double max;


	//TODO y si se usa alpha = nextGaussian centrado en cero y con stdev de 1/3 ?
	public RecombinacionAritmeticaCompleta(Funcion f, double alpha, 
											double probabilidad, 
											Aleatorio aleatorio) 
	{
		this.alpha = alpha;
		this.probabilidad = probabilidad;
		this.aleatorio = aleatorio;
		
		setFuncion(f);
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		//TODO usar RecombinacionProbable para esto!
		if (!aleatorio.isMenorQue(probabilidad)) {
			return null;
		}
		
		double[] v1 = i1.getValores();
		double[] v2 = i2.getValores();
		
		double[] v11 = new double[v1.length];
		double[] v22 = new double[v2.length];
		
		for (int i = 0; i < v1.length; i++) {
			v11[i] = alpha * v1[i] + (1 - alpha) * v2[i];
			v22[i] = alpha * v2[i] + (1 - alpha) * v1[i];
			
			v11[i] = Utils.clamp(v11[i], min, max);
			v22[i] = Utils.clamp(v22[i], min, max);
		}
		
		//TODO cambiarle la iteracion, no es cero!
		Individuo i11 = new Individuo(0, v11, funcion);
		Individuo i22 = new Individuo(0, v22, funcion);

		return new Individuo[] { i11, i22 };
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
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
