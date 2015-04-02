package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Contenedor (wrapper) de otra Recombinacion para agregar la posibilidad de no
 * recombinar a los padres bajo cierta probabilidad.
 * En caso de que sí deban recombinarse, se delega eso a la recombinacion 
 * interna. 
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecombinacionProbable implements Recombinacion {

	private Recombinacion recombinacion;
	private Aleatorio aleatorio;
	private int probabilidad;
	
	public RecombinacionProbable(Recombinacion recombinacion, 
								  int probabilidad, Aleatorio aleatorio) 
	{
		this.recombinacion = recombinacion;
		this.aleatorio = aleatorio;
		this.probabilidad = probabilidad;
	}
	
	@Override
	public Individuo[] getCrias(Individuo i1, Individuo i2) {
		if (!aleatorio.isMenorQue(probabilidad)) {
			return null;
		}
		
		return recombinacion.getCrias(i1, i2);
	}

	@Override
	public String toString() {
		return "Recombinacion_Probable(" + probabilidad + ", " + 
				recombinacion + ")";
	}
	
	@Override
	public void set(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
		this.recombinacion.set(aleatorio);
	}
	
	@Override
	public void setFuncion(Funcion f) {
		this.recombinacion.setFuncion(f);
	}
}

