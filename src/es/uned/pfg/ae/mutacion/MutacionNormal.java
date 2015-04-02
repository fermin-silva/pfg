package es.uned.pfg.ae.mutacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * Mutacion no uniforme con una distribución normal fija.
 * Diseñada para que la mutación sea, por lo general, muy pequeña. Aunque con baja 
 * probabilidad puede producir grandes cambios.
 * Para ello utiliza una función normal centrada en cero y con una desviación estandar
 * proporcionada por el usuario.
 *   
 * @author fermin
 */
public class MutacionNormal implements Mutacion {

	private Aleatorio aleatorio;
	private double desviacion;
	private double prob;
	private double min;
	private double max;

	
	public MutacionNormal(double desviacion, Aleatorio aleatorio, double min, 
						   double max) 
	{
		this.aleatorio = aleatorio;
		this.desviacion = desviacion;
		this.min = min;
		this.max = max;
		this.prob = 1.0;
	}

	@Override
	public void mutar(Individuo i) {
		double[] alelos = i.getValores();
		
		boolean cambiado = false;
		
		for (int j = 0; j < alelos.length; j++) {
			if (aleatorio.isMenorQue(prob)) {
				alelos[j] = Utils.clamp(alelos[j] + aleatorio.getNormal(0, desviacion),
										min, max);
				cambiado = true;
			}
		}
		
		if (cambiado) {
			i.calcularFitness();
		}
	}

	public void iteracion() {
		desviacion -= 1;
		
		if (desviacion <= 0) {
			desviacion = 1;
		}
	}

	@Override
	public void set(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
	}
	
	public void setMin(double min) {
		this.min = min;
	}
	
	public void setMax(double max) {
		this.max = max;
	}
}
