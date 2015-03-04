package es.uned.pfg.ae;

import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.terminacion.Terminacion;

public class AlgoritmoGenetico {

	private Poblacion poblacion;
	private Seleccion seleccion;
	private Configuracion conf;
	private Recombinacion recombinacion;
	private Mutacion mutacion;
	private Terminacion terminacion;

	
	public AlgoritmoGenetico(Configuracion conf, Poblacion poblacionInicial, 
							 Seleccion seleccion, Recombinacion recombinacion,
							 Mutacion mutacion, Terminacion terminacion) 
	{
		this.conf = conf;
		this.poblacion = poblacionInicial;
		this.seleccion = seleccion;
		this.recombinacion = recombinacion;
		this.mutacion = mutacion;
		this.terminacion = terminacion;
	}
	
	public void comenzar() {
		boolean terminado = false;
		
		for (int i = 1; i < conf.getMaxIteraciones() && !terminado; i++) {
			terminado = iteracion(i);
		}
	}
	
	public boolean iteracion(int iteracion) {
		//TODO 1) seleccionar padres
		Individuo[] padres = seleccion.seleccionar(poblacion, conf.getCantPadres());
		
		//TODO 2) recombinar pares de padres
		Individuo[] crias = recombinacion.getCrias(padres);
		
		//TODO 3) mutar las crias
		//TODO 4) evaluar a las crias
		for (Individuo individuo : crias) {
			mutacion.mutar(individuo);
		}
		
		//TODO 5) seleccionar supervivientes para la nueva generacion (padres + crias)
		poblacion.setSobrevivientes(padres, crias);
		
		//TODO 6) agregar condicion de terminacion (si es que hace falta)
		return terminacion.isTerminado(iteracion, poblacion);
	}
}
