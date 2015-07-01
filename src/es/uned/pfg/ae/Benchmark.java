package es.uned.pfg.ae;

import es.uned.pfg.ae.chart.BenchmarkPlot;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionFactory;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionFactory;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionFactory;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionFactory;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.terminacion.TerminacionFija;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Modo de ejecucion que prueba todas las funciones y recombinaciones,
 * imprimiendo resultados y obteniendo estadisticas sobre cada una de ellas.
 *
 * @author Fermin Silva
 */
public class Benchmark {

	public static final int RUNS = 100;

	//TODO parametrizar
	public static final int ANCHO = 640;
	public static final int ALTO = 480;


	public Benchmark(final Configuracion conf) {
		Seleccion seleccion = SeleccionFactory.crear(conf);
		Mutacion mutacion = MutacionFactory.crear(conf);
		Funcion[] fs = FuncionFactory.crearBenchmark(conf);
		Recombinacion[] recombinaciones = RecombinacionFactory.crearBenchmark(conf);
		
		Terminacion terminacion = new TerminacionFija(conf.getGeneraciones());

		//calentamiento para evitar mal rendimiento de la primera recombinacion
		for (Recombinacion r : recombinaciones) {
			//solo se ejecuta, no se imprime ni genera grafico
			ejecucion(conf, seleccion, mutacion, r, terminacion, fs[0], null);
		}

		System.out.println(seleccion);
		System.out.println(mutacion);
		System.out.println("Iteraciones: " + conf.getGeneraciones());
		System.out.println("Poblacion: " + conf.getTamañoPoblacion());
		System.out.println();
		
		ResultadoBenchmark resultado = null;
		
		System.out.println("Recombinacion\tFuncion\tMin_fit\tAvg_fit\tMax_fit\tStdev\tAvg_time");

		//por cada funcion probar cada recombinacion n cantidad de veces
		for (Funcion f : fs) {
			//crear un graficador para las pruebas con esta funcion
			BenchmarkPlot plot = new BenchmarkPlot(f, conf.getGeneraciones());

			for (Recombinacion recombinacion : recombinaciones) {
				//crear el objeto resultado para almacenar el rendimiento de
				//las n ejecuciones (minimo, maximo, promedio, etc)
				resultado = new ResultadoBenchmark(f, recombinacion);

				for (int i = 1; i <= RUNS; i++) {
					ejecucion(conf, seleccion, mutacion, recombinacion,
							  terminacion, f, resultado);
				}

				System.out.println(resultado);
				plot.agregar(resultado);
			}

			plot.guardar(ANCHO, ALTO);
		}
	}

	/**
	 * Ejecucion en si del algoritmo genetico una unica vez
	 */
	protected void ejecucion(Configuracion conf, Seleccion seleccion, 
							 Mutacion mutacion, Recombinacion recombinacion,
							 Terminacion terminacion, Funcion f,
							 ResultadoBenchmark resultado)
	{
		Aleatorio aleatorio = new Aleatorio();
		
		seleccion.set(aleatorio);
	
		mutacion.set(aleatorio);
		mutacion.setMin(f.getMin());
		mutacion.setMax(f.getMax());
		
		recombinacion.set(aleatorio);
		recombinacion.setFuncion(f);
		
		int tamaño = conf.getTamañoPoblacion();
		Individuo[] individuos = Individuo.getIndividuosInicial(tamaño, f,
																aleatorio);
		
		Poblacion poblacion = new PoblacionGeneracional(individuos, 
														conf.getElitismo());
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(poblacion, seleccion, 
								      				 recombinacion, mutacion, 
								      				 terminacion);
		
		ag.comenzar();

		if (resultado != null) {
			resultado.recolectar(ag);
		}
	}
}

