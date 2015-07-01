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
 * @author Fermin Silva
 */
public class MutacionNormal implements Mutacion {

	private Aleatorio aleatorio;
	private double desviacion;
	private double prob;
	private double min;
	private double max;

	public MutacionNormal() {
		
	}
	
	@Override
	public void mutar(Individuo i) {
		double[] alelos = i.getValores();
		
		boolean cambiado = false;
		
		for (int j = 0; j < alelos.length; j++) {
			//generar un numero aleatorio y compararlo con la probabilidad de
			//mutacion
			if (aleatorio.isMenorQue(prob)) {
				double mutacion = aleatorio.getNormal(0, desviacion);

				//restringir el valor si este sale del intervalo permitido
				alelos[j] = Utils.clamp(alelos[j] + mutacion, min, max);
				cambiado = true;
			}
		}
		
		if (cambiado) {
			//recalcular la fitness del individuo ya que se han cambiado sus
			//genes sin que este haya tenido control sobre ello
			i.calcularFitness();
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

	@Override
	public void setProb(double prob) {
		this.prob = prob;
	}

	public void setDesviacion(double desviacion) {
		this.desviacion = desviacion;
	}
	
	@Override
	public String toString() {
		return "MutacionNormal (" + desviacion + ")";
	}
}
