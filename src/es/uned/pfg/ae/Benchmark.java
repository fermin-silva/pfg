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
import es.uned.pfg.ae.mutacion.MutacionNormal;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionK;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionTorneo;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.utils.Aleatorio;

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
		for (Funcion f : fs) {
			for (Recombinacion recombinacion : recombinaciones) {
				
				System.out.println(recombinacion + " + " + f);
				
				for (int i = 0; i < RUNS; i++) {
					Individuo mejor = ejecucion(conf, seleccion, mutacion, 
											    recombinacion, terminacion, f);
					
					System.out.println("Ejecucion " + i + ", mejor: " + mejor);
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
				new FuncionAckley(10),
				new FuncionGriewank(10),
				new FuncionRastrigin(10),
				new FuncionSchaffer2(),
				new FuncionSchubert(),
				new FuncionSchwefel(10),
				
		};
		
		Recombinacion[] recombs = new Recombinacion[]{
				
			new RecombinacionK(ALEATORIO_DEFAULT, funciones[0], 2, 0.2)
		};

		Seleccion seleccion = new SeleccionTorneo(4, ALEATORIO_DEFAULT);
		
		Mutacion mutacion = new MutacionNormal(5, ALEATORIO_DEFAULT, 
											   funciones[0].getMin(), 
											   funciones[0].getMax());
		
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

