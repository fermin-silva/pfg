package es.uned.pfg.ae;

import es.uned.pfg.ae.chart.BasePlot;
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
import es.uned.pfg.ae.terminacion.TerminacionFija;
import es.uned.pfg.ae.utils.Utils;
import es.uned.pfg.ae.web.Servidor;

import javax.rmi.CORBA.Util;
import java.util.Arrays;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Bootstrap {

	public static void main(String[] args) throws Exception {
		Configuracion conf = new Configuracion(Parametros.crear(args));

		if (conf.isBenchmark()) {
			new Benchmark(conf);
		}
		else if (conf.getPuerto() != null) {
			Servidor servidor = new Servidor(conf);
			servidor.start();
			servidor.join();
		}
		else {
			correrConsola(conf);
		}
	}

	private static void correrConsola(Configuracion conf) {
		Funcion f = FuncionFactory.crear(conf);
		Seleccion seleccion = SeleccionFactory.crear(conf);
		Recombinacion recombinacion = RecombinacionFactory.crear(conf);
		Mutacion mutacion = MutacionFactory.crear(conf);

		int t = conf.getTamañoPoblacion();
		Individuo[] individuos = Individuo.getIndividuosInicial(t, f);

		Poblacion poblacion = new PoblacionGeneracional(individuos, conf.getElitismo());

		Terminacion terminacion = new TerminacionFija(conf.getGeneraciones());

		System.out.println("Funcion: " + f);
		System.out.println("Seleccion: " + seleccion);
		System.out.println("Recombinacion: " + recombinacion);
		System.out.println("Mutacion: " + mutacion);
		System.out.println("Tamaño poblacional: " + t);
		System.out.println("Generaciones: " + conf.getGeneraciones());
		System.out.println("");

		AlgoritmoGenetico ag = new AlgoritmoGenetico(poblacion, seleccion,
													 recombinacion, mutacion,
													 terminacion);

		ag.comenzar();

		Individuo mejor = poblacion.getMejorIndividuo();

		double mejorFitnes = mejor.getFitness();
		double[] mejorCoordenadas = mejor.getValores();
		double[] centroide = poblacion.getCentroide();

		double desviacion = poblacion.getDesviacion();
		double tiempo = ag.getTiempo();

		imprimirResultado(mejorFitnes, mejorCoordenadas, centroide, desviacion,
						  tiempo);

		BasePlot basePlot = new BasePlot(f, conf.getGeneraciones());
		basePlot.setMostrarLeyenda(false);
		basePlot.agregar("nombre", ag.getCurvaProgreso(), ag.getMomentosInercia());
		basePlot.guardar(Benchmark.ANCHO, Benchmark.ALTO);
	}

	private static void imprimirResultado(double mejor, double[] coords,
										  double[] centroide, double desviacion,
										  double tiempo)
	{
		System.out.println("-------------------");
		System.out.println("Progreso finalizado");
		System.out.println("-------------------");
		System.out.println("");
		System.out.println("Tiempo de Ejecucion: " +
						   (int)(tiempo) + "ms");
		System.out.println("");
		System.out.println("Mejor Individuo");
		System.out.println("\tFitness: " + Utils.toString(mejor));
		System.out.println("\tCoordenadas: ");
		for (int i = 0; i < coords.length; i++) {

			System.out.println("\t\t" + padIzq("x" + (i + 1), 3) + ": " +
					Utils.toString(coords[i]));
		}
		System.out.println("");
		System.out.println("Centroide de la Poblacion (promedio):");
		for (int i = 0; i < coords.length; i++) {
			System.out.println("\t\t" + padIzq("x" + (i + 1), 3) + ": " +
								Utils.toString(centroide[i]));
		}
		System.out.println("");
		System.out.println("Desviacion Tipica de la poblacion con respecto " +
						   "al centroide: " + Utils.toString(desviacion));
		System.out.println("");
	}

	private static String padIzq(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}
}
