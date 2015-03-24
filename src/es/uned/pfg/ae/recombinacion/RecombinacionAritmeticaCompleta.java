package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

public class RecombinacionAritmeticaCompleta implements Recombinacion {

	private Funcion funcion;
	private double alpha;
	private double probabilidad;
	
	private Aleatorio aleatorio;


	//TODO y si se usa alpha = nextGaussian centrado en cero y con stdev de 1/3 ?
	public RecombinacionAritmeticaCompleta(Funcion f, double alpha, 
											double probabilidad, 
											Aleatorio aleatorio) 
	{
		this.funcion = f;
		this.alpha = alpha;
		this.probabilidad = probabilidad;
		this.aleatorio = aleatorio;
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		if (!aleatorio.isMenorQue(probabilidad)) {
			return new Individuo[]{ i1.clone(), i2.clone() };
		}
		
		double[] v1 = i1.getValores();
		double[] v2 = i2.getValores();
		
		double[] v11 = new double[v1.length];
		double[] v22 = new double[v2.length];
		
		for (int i = 0; i < v1.length; i++) {
			v11[i] = alpha * v1[i] + (1 - alpha) * v2[i];
			v22[i] = alpha * v2[i] + (1 - alpha) * v1[i];
		}
		
		//TODO cambiarle la iteracion, no es cero!
		Individuo i11 = new Individuo(0, v11, funcion);
		Individuo i22 = new Individuo(0, v22, funcion);

		return new Individuo[] { i11, i22 };
	}
}
