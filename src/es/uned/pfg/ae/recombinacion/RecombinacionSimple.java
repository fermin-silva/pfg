package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

public class RecombinacionSimple implements Recombinacion {

	private Aleatorio aleatorio;
	private Funcion funcion;

	public RecombinacionSimple(Aleatorio aleatorio, Funcion f) {
		this.aleatorio = aleatorio;
		this.funcion = f;
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] v1 = i1.getValores();
		double[] v2 = i2.getValores();
		
		double[] v11 = new double[v1.length];
		double[] v22 = new double[v2.length];
		
		
		int puntoCruce = aleatorio.getPosicion(v1);

		System.arraycopy(v1, 0, v11, 0, puntoCruce);
		System.arraycopy(v2, 0, v22, 0, puntoCruce);
		
		System.arraycopy(v2, puntoCruce, v11, puntoCruce, v2.length - puntoCruce);
		System.arraycopy(v1, puntoCruce, v22, puntoCruce, v1.length - puntoCruce);
		
		//TODO cambiarle la iteracion, no es cero!
		return new Individuo[] { new Individuo(0, v11, funcion), 
								 new Individuo(0, v22, funcion) };
	}
}
