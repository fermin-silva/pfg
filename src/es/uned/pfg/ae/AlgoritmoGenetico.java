package es.uned.pfg.ae;

import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.terminacion.Terminacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del proyecto que realiza la ejecucion del Algoritmo Genetico.
 *
 * @author Fermin Silva
 */
public class AlgoritmoGenetico {

	private Poblacion poblacion;
	private Seleccion seleccion;
	private Recombinacion recombinacion;
	private Mutacion mutacion;
	private Terminacion terminacion;
	private List<Double> curvaProgreso;
	private List<Double> curvaCovergencia;
	
	private long tiempo = -1;
	
	public AlgoritmoGenetico(Poblacion poblacionInicial, Seleccion seleccion, 
							  Recombinacion recombinacion, Mutacion mutacion, 
							  Terminacion terminacion) 
	{
		this.poblacion = poblacionInicial;
		this.seleccion = seleccion;
		this.recombinacion = recombinacion;
		this.mutacion = mutacion;
		this.terminacion = terminacion;
		this.curvaProgreso = new ArrayList<Double>();
		this.curvaCovergencia = new ArrayList<Double>();
	}
	
	public void comenzar() {
		boolean terminado = false;
		long start = System.currentTimeMillis();
		
		for (int i = 1; !terminado; i++) {
			terminado = iteracion(i);
		}
		
		tiempo = System.currentTimeMillis() - start;
	}

	/**
	 * Ejecuta una iteracion o generacion del algoritmo.
	 * Devuelve verdadero o falso segun si se ha alcanzado el criterio de
	 * terminacion o no.
	 */
	public boolean iteracion(int iteracion) {
		Individuo[] padres = seleccion.seleccionar(poblacion);
		
		Individuo[] crias = new Individuo[padres.length];

		//se recorren los padres de a dos en dos
		for (int i = 0; i < padres.length - 1; i += 2) {
			Individuo[] c = recombinacion.getCrias(padres[i], padres[i + 1]);
			
			if (c == null || c.length == 0) { //no se han recombinado
				//se copian a la nueva generacion sin mutaciones
				crias[i] = padres[i];
				crias[i + 1] = padres[i + 1];
			}
			else {
				for (Individuo cria : c) {
					mutacion.mutar(cria);
				}
				
				crias[i] = c[0];
				crias[i + 1] = c[1];				
			}
		}
		
		poblacion.setSobrevivientes(crias);
	
		curvaProgreso.add(poblacion.getMejorIndividuo().getFitness());
		curvaCovergencia.add(poblacion.getDesviacion());

		return terminacion.isTerminado(iteracion, poblacion);
	}

	public List<Double> getCurvaCovergencia() {
		return curvaCovergencia;
	}

	public Individuo getMejorIndividuo() {
		return poblacion.getMejorIndividuo();
	}
	
	public List<Double> getCurvaProgreso() {
		return curvaProgreso;
	}
	
	public long getTiempo() {
		return tiempo;
	}
}
