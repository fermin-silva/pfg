package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

public class RecombinacionAritmeticaSimple implements Recombinacion {

	private Aleatorio aleatorio;
	private Funcion funcion;

	public RecombinacionAritmeticaSimple(Aleatorio aleatorio, Funcion f) {
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

		for (int i = 0; i < v1.length; i++) {
			if (i >= puntoCruce) {
				double avg = (v1[i] + v2[i]) / 2.0;
				v11[i] = avg;
				v22[i] = avg;
			}
			else {
				v11[i] = v1[i];
				v22[i] = v2[i];
			}
		}
		
		//TODO cambiarle la iteracion, no es cero!
		return new Individuo[] { new Individuo(0, v11, funcion), 
								 new Individuo(0, v22, funcion) };
	}

}
