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
		Individuo[] padres = seleccion.seleccionar(poblacion);
		
		//TODO la seleccion deberia devolver grupos de a 2 padres para recombinar
		
		Individuo[] crias = new Individuo[padres.length];
			
		for (int i = 0; i < padres.length - 1; i += 2) {
			Individuo[] c = recombinacion.getCrias(padres[i], padres[i + 1]);
			
			if (c == null || c.length == 0) { //no se han recombinado
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
		
		return terminacion.isTerminado(iteracion, poblacion);
	}
}
