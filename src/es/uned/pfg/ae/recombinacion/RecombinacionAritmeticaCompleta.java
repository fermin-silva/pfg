package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

public class RecombinacionAritmeticaCompleta implements Recombinacion {

	private Funcion funcion;
	private double alpha;


	//TODO y si se usa alpha = nextGaussian centrado en cero y con stdev de 1/3 ?
	public RecombinacionAritmeticaCompleta(Funcion f, double alpha) {
		this.funcion = f;
		this.alpha = alpha;
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] v1 = i1.getValores();
		double[] v2 = i2.getValores();
		
		double[] v11 = new double[v1.length];
		double[] v22 = new double[v2.length];
		
		for (int i = 0; i < v1.length; i++) {
			v11[i] = alpha * v1[i] + (1 - alpha) * v2[i];
			v22[i] = alpha * v2[i] + (1 - alpha) * v1[i];
		}
		
		//TODO cambiarle la iteracion, no es cero!
		return new Individuo[] { new Individuo(0, v11, funcion), 
								 new Individuo(0, v22, funcion) };
	}
}
