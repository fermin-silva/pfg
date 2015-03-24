package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

public class RecombinacionAritmeticaSimple implements Recombinacion {

	private Aleatorio aleatorio;
	private Funcion funcion;
	private boolean lineaAleatoria;
	private double alpha;
	private double min;
	private double max;

	public RecombinacionAritmeticaSimple(Aleatorio aleatorio, Funcion f,
										  boolean lineaAleatoria,
										  double min, double max) 
	{
		this.aleatorio = aleatorio;
		this.funcion = f;
		this.lineaAleatoria = lineaAleatoria;
//		this.alpha = aleatorio.get();
		this.alpha = aleatorio.getEntre(-0.1, 0.4); //puede salir un poco del espacio interior de los padres!
		this.min = min;
		this.max = max;
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] v1 = i1.getValores();
		double[] v2 = i2.getValores();
		
		double[] v11 = new double[v1.length];
		double[] v22 = new double[v2.length];
		
		int puntoCruce = aleatorio.getPosicion(v1);
		int puntoCruce2 = aleatorio.getPosicion(v1);
		
		//originalmente usa 1 solo punto de cruce en vez de 2, hace un avg en vez de linea y no usa alpha
		//y ademas hace if i >= puntoCruce: pero promediando varios genes no performa bien!
		for (int i = 0; i < v1.length; i++) {
			if (i == puntoCruce || i == puntoCruce2) {
				if (lineaAleatoria) {
					//modificado
//					double min = Math.min(v1[i], v2[i]);
//					double max = Math.max(v1[i], v2[i]);
//					double delta = max - min;
//					
//					v11[i] = min + alpha * delta;
//					v22[i] = min + (1 - alpha) * delta;
					//end modificado
					
					//original de AritmeticaCompleta
					v11[i] = alpha * v1[i] + (1 - alpha) * v2[i];
					v22[i] = alpha * v2[i] + (1 - alpha) * v1[i];
					
					v11[i] = Utils.clamp(v11[i], min, max);
					v22[i] = Utils.clamp(v22[i], min, max);
				}
				else {
					//original del libro de AritmeticaSimple
					double avg = (v1[i] + v2[i]) / 2.0;
					v11[i] = avg;
					v22[i] = avg;
				}
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
