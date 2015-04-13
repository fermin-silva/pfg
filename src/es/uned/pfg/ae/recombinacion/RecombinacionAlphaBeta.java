package es.uned.pfg.ae.recombinacion;

import java.util.Arrays;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

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
	
//		Asi me da a mi en el cuaderno, pero la version que intercambia el gen de padre funciona mejor
//		if (i1.getFitness() > i2.getFitness()) {
//			x2[i] = (1 - alpha) * x[i] + alpha * y[i];
//			y2[i] = (1 - beta)  * y[i] +  beta * x[i];
//		}
//		else {
//			x2[i] = (1 - beta)  * x[i] +  beta * y[i];
//			y2[i] = (1 - alpha) * y[i] + alpha * x[i];
//		}
		
		//asi viene en el libro: el gen i de x viene del padre y, y viceversa
		if (i1.getFitness() > i2.getFitness()) {
			y2[i] = (1 - alpha) * x[i] + alpha * y[i];
			x2[i] = (1 - beta)  * y[i] +  beta * x[i];
		}
		else {
			y2[i] = (1 - beta)  * x[i] +  beta * y[i];
			x2[i] = (1 - alpha) * y[i] + alpha * x[i];
		}
		
		//TODO estudiar el espacio de soluciones segun posibles valores de alpha y beta (donde pueden caer las crias)
		
		x2[i] = Utils.clamp(x2[i], min, max);
		y2[i] = Utils.clamp(y2[i], min, max);			
		
		//TODO cambiarle la iteracion, no es cero!
		return new Individuo[] { new Individuo(0, x2, funcion), 
								 new Individuo(0, y2, funcion) };
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
