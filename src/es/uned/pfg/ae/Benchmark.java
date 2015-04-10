package es.uned.pfg.ae;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionFactory;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionFactory;
import es.uned.pfg.ae.params.Parametros;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionFactory;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionFactory;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Benchmark {

	public static final int RUNS = 10;
	
	public Benchmark(final Configuracion conf) {
		Seleccion seleccion = SeleccionFactory.crear(conf);
		Mutacion mutacion = MutacionFactory.crear(conf);
		Funcion[] fs = FuncionFactory.crearBenchmark(conf);
		Recombinacion[] recombinaciones = RecombinacionFactory.crearBenchmark(conf);
		
		Terminacion terminacion = new Terminacion() {
			@Override
			public boolean isTerminado(int iteracion, Poblacion p) {
				return iteracion > conf.getGeneraciones();
			}
		};
		
		System.out.println(seleccion);
		System.out.println(mutacion);
		System.out.println("Iteraciones: " + conf.getGeneraciones());
		System.out.println("Poblacion: " + conf.getTamañoPoblacion());
		System.out.println();
		
		ResultadoBenchmark resultado = null;
		
		System.out.println("Recombinacion\tFuncion\tMin_fit\tAvg_fit\tMax_fit\tStdev\tAvg_time");
		for (Funcion f : fs) {
			for (Recombinacion recombinacion : recombinaciones) {
				resultado = new ResultadoBenchmark(f, recombinacion);
				
				for (int i = 1; i <= RUNS; i++) {
					ejecucion(conf, seleccion, mutacion, recombinacion, 
							  terminacion, f, resultado);
				}
				
				System.out.println(resultado);
			}
		}
	}
	
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
		Individuo[] individuos = getIndividuosInicial(tamaño, f, aleatorio);
		
		Poblacion poblacion = new PoblacionGeneracional(individuos, 
														conf.getElitismo());
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(poblacion, seleccion, 
								      				 recombinacion, mutacion, 
								      				 terminacion);
		
		ag.comenzar();
		resultado.recolectar(ag);
	}
	
	public static Individuo[] getIndividuosInicial(int tamaño, Funcion f,
													 Aleatorio aleatorio) 
	{
		int dimension = f.getDimension();
		
		Individuo[] individuos = new Individuo[tamaño];
		
		for (int i = 0; i < individuos.length; i++) {
			double[] valores = new double[dimension];
			
			for (int j = 0; j < valores.length; j++) {
				valores[j] = aleatorio.getEntre(f.getMin(), f.getMax());
			}
			
			individuos[i] = new Individuo(i, valores, f);
		}
		
		return individuos;
	}
	
	//TODO eliminar este main de prueba
	public static void main(String[] args) {
		final Configuracion conf = new Configuracion(Parametros.crear(args));

		new Benchmark(conf);
	}
}

