package es.uned.pfg.ae;

import static es.uned.pfg.ae.Configuracion.ALEATORIO_DEFAULT;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionAckley;
import es.uned.pfg.ae.funcion.FuncionGriewank;
import es.uned.pfg.ae.funcion.FuncionRastrigin;
import es.uned.pfg.ae.funcion.FuncionSchaffer2;
import es.uned.pfg.ae.funcion.FuncionSchubert;
import es.uned.pfg.ae.funcion.FuncionSchwefel;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionNoOp;
import es.uned.pfg.ae.mutacion.MutacionNormal;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionAritmeticaCompleta;
import es.uned.pfg.ae.recombinacion.RecombinacionK;
import es.uned.pfg.ae.recombinacion.RecombinacionNoOp;
import es.uned.pfg.ae.recombinacion.RecombinacionSimple;
import es.uned.pfg.ae.recombinacion.RecombinacionUnica;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionTorneo;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Benchmark {

	public static final int RUNS = 10;
	
	public Benchmark(Funcion[] fs, Seleccion seleccion, Mutacion mutacion,
					  Terminacion terminacion, Configuracion conf, 
					  Recombinacion[] recombinaciones) 
	{
		System.out.println(seleccion);
		System.out.println(mutacion);
		System.out.println("Iteraciones: " + conf.getMaxIteraciones());
		System.out.println("Poblacion: " + conf.getTamañoPoblacion());
		System.out.println();
		
		for (Funcion f : fs) {
			for (Recombinacion recombinacion : recombinaciones) {
				
				System.out.println(recombinacion + " + " + f);
				System.out.println("Ejecucion\tMejor_Individuo\tRuntime");
				
				
				for (int i = 1; i <= RUNS; i++) {
					long start = System.currentTimeMillis();
					
					Individuo mejor = ejecucion(conf, seleccion, mutacion, 
											    recombinacion, terminacion, f);
					
					long end = System.currentTimeMillis();
					double segundos = (end - start) / 1000.0;
					
					System.out.println("   " + i + "\t" + 
							   		   Utils.toString(mejor.getFitness()) + "\t" + 
									   Utils.toString(segundos));
				}
				
				System.out.println();
			}
		}
	}
	
	protected Individuo ejecucion(Configuracion conf, Seleccion seleccion, 
							       Mutacion mutacion, Recombinacion recombinacion,
							       Terminacion terminacion, Funcion f) 
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
		
		Poblacion poblacion = new PoblacionGeneracional(individuos, true);
		
		AlgoritmoGenetico ag = 
				new AlgoritmoGenetico(conf, poblacion, seleccion, 
								      recombinacion, mutacion, 
								      terminacion);
		
		ag.comenzar();
		
		return poblacion.getMejorIndividuo();
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
		final Configuracion conf = new Configuracion();

		
		Funcion[] funciones = new Funcion[] {
				
				new FuncionAckley(10),
				new FuncionGriewank(10),
				new FuncionRastrigin(10),
				new FuncionSchaffer2(),
				new FuncionSchubert(),
				new FuncionSchwefel(10),
				
		};
		
		double alpha = 0.2;
		
		Recombinacion[] recombs = new Recombinacion[]{
				
			new RecombinacionNoOp(),
			new RecombinacionAritmeticaCompleta(funciones[0], alpha, 0.5, ALEATORIO_DEFAULT),
			new RecombinacionSimple(ALEATORIO_DEFAULT, funciones[0], alpha),
			new RecombinacionUnica(ALEATORIO_DEFAULT, funciones[0], alpha),
			new RecombinacionK(ALEATORIO_DEFAULT, funciones[0], 2, alpha),
		};

		Seleccion seleccion = new SeleccionTorneo(2, ALEATORIO_DEFAULT);
		
		//los valores altos de mutacion generan problemas en espacios pequeños
		//como el de rastrigin (-5, 5), aunque ayudan en los grandes (-600, 600)
		Mutacion mutacion = new MutacionNormal(0.1, ALEATORIO_DEFAULT, 
											   funciones[0].getMin(), 
											   funciones[0].getMax());
		
//		Mutacion mutacion = new MutacionNoOp();
		
		Terminacion terminacion = new Terminacion() {
			@Override
			public boolean isTerminado(int iteracion, Poblacion p) {
				return iteracion > conf.getMaxIteraciones();
			}
		};
		
		new Benchmark(funciones, seleccion, mutacion, terminacion, 
					  conf, recombs);
	}
}

