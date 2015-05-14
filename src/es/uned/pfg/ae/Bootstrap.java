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

import java.util.Arrays;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Bootstrap {

	public static void main(String[] args) throws Exception {
		//TODO profilear usando el parametro -J-agentlib:hprof=cpu=times
		// http://docs.oracle.com/javase/7/docs/technotes/samples/hprof.html

		Configuracion conf = new Configuracion(Parametros.crear(args));

		if (conf.getBenchmark()) {
			new Benchmark(conf);
		}
		else {
			correrConsola(conf);
		}
		//TODO corrida rest/http?
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


		AlgoritmoGenetico ag = new AlgoritmoGenetico(poblacion, seleccion,
													 recombinacion, mutacion,
													 terminacion);

		ag.comenzar();

		individuos = poblacion.getIndividuos();
		Arrays.sort(individuos);

		int top = 20;

		System.out.println("=== Top " + top + " Individuos ===");
		for (int i = 0; i < Math.min(top, individuos.length); i++) {
			System.out.println(individuos[i]);
		}

		BasePlot basePlot = new BasePlot(f, conf.getGeneraciones());
		basePlot.setMostrarLeyenda(true);
		basePlot.agregar("nombre", ag.getCurvaProgreso(), ag.getMomentosInercia());
		basePlot.guardar(Benchmark.ANCHO, Benchmark.ALTO);
	}
}
