package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

import java.util.Arrays;

/**
 * Recombinacion propuesta por el alumno, no forma parte de la bibliografia.
 * Utiliza dos valores para el promedio aritmetico: alfa para la distancia del
 * padre con mejor fitness y beta para la distancia del peor padre. Se asume
 * que alfa < beta. <br>
 * La intuicion es generar a los hijos mas cerca del mejor padre, en vez de a
 * distancias iguales. <br>
 * En la memoria se explica con mayor profundidad.
 *
 * @author Fermin Silva
 */
public class RecombinacionAlphaBeta extends RecombinacionAlpha {

	private double beta;
	private Aleatorio aleatorio;
	
	private Funcion funcion;

	private double min;
	private double max;
	
	
	public RecombinacionAlphaBeta() {
		
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		double[] x = i1.getValores();
		double[] y = i2.getValores();
		
		int n = x.length;
		
		//se copian los arrays enteros, luego se sobreescriben algunos alelos
		double[] x2 = Arrays.copyOf(x, n);
		double[] y2 = Arrays.copyOf(y, n);
		
		int i = aleatorio.getInt(n); //punto de cruce

		//si el primer padre es mejor que el segundo
		if (i1.getFitness() > i2.getFitness()) {
			y2[i] = (1 - alpha) * x[i] + alpha * y[i];
			x2[i] = (1 - beta)  * y[i] +  beta * x[i];
		}
		else { //si el segundo es mejor que el primero
			y2[i] = (1 - beta)  * x[i] +  beta * y[i];
			x2[i] = (1 - alpha) * y[i] + alpha * x[i];
		}
		
		x2[i] = Utils.clamp(x2[i], min, max);
		y2[i] = Utils.clamp(y2[i], min, max);			
		
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

	public void setBeta(double beta) {
		this.beta = beta;
	}
	
	@Override
	public String toString() {
		return "RecombinacionAlphaBeta (" + alpha + ", " + beta + ")";  
	}
}
