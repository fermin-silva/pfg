package es.uned.pfg.ae;

import es.uned.pfg.ae.chart.BenchmarkPlot;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionFactory;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionFactory;
import es.uned.pfg.ae.params.Parametros;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionFactory;
import es.uned.pfg.ae.recombinacion.RecombinacionK;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionFactory;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.terminacion.TerminacionFija;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Prueba el algoritmo para distintos valores de k entre 1 y 7.
 *
 * @author Fermin Silva < fermins@olx.com >
 */
public class TestValorK {

    public static final int RUNS = 150;
    public static final int ANCHO = 640;
    public static final int ALTO = 480;


    public static void main(String[] args) {
        args = ("-elitismo -benchmark -g 100 -t 2000 -s Torneo -m Normal " +
                "-desviacion 30 -tamañoTorneo 2 -d 20 -alpha 0.1 " +
                "-r K").split(" ");

        Configuracion conf = new Configuracion(Parametros.crear(args));

        Funcion f = FuncionFactory.crear(conf);
        Seleccion s = SeleccionFactory.crear(conf);
        RecombinacionK r = (RecombinacionK)RecombinacionFactory.crear(conf);
        Mutacion m = MutacionFactory.crear(conf);

        Terminacion t = new TerminacionFija(conf.getGeneraciones());

        //calentamiento para evitar rendimiento pobre de la primera ejecucion
        ejecucion(s, m, r, t, f, null, conf);

        System.out.println(s);
        System.out.println(m);
        System.out.println("Iteraciones: " + conf.getGeneraciones());
        System.out.println("Poblacion: " + conf.getTamañoPoblacion());
        System.out.println();

        ResultadoBenchmark resultado = null;

        System.out.println("Recombinacion\tFuncion\tMin_fit\tAvg_fit\tMax_fit\tStdev\tAvg_time");
        BenchmarkPlot plot = new CustomBenchmarkPlot(f, conf.getGeneraciones());

        for (int i = 7; i >= 1; i--) {
            r.setK(i);
            resultado = new ResultadoBenchmark(f, r);

            for (int j = 1; j <= RUNS; j++) {
                ejecucion(s, m, r, t, f, resultado, conf);
            }

            System.out.println(resultado);
            plot.agregar(resultado);
        }

        plot.guardar(ANCHO, ALTO);
    }

    private static void ejecucion(Seleccion seleccion, Mutacion mutacion,
                                  Recombinacion recombinacion,
                                  Terminacion terminacion, Funcion f,
                                  ResultadoBenchmark resultado,
                                  Configuracion conf)
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

        Poblacion poblacion = new PoblacionGeneracional(individuos, true);

        AlgoritmoGenetico ag = new AlgoritmoGenetico(poblacion, seleccion,
                                                     recombinacion, mutacion,
                                                     terminacion);

        ag.comenzar();
        if (resultado != null) {
            resultado.recolectar(ag);
        }
    }

    private static class CustomBenchmarkPlot extends BenchmarkPlot {

        public CustomBenchmarkPlot(Funcion f, int maxGeneraciones) {
            super(f, maxGeneraciones);
        }

        @Override
        public String getNombre(Recombinacion r) {
            RecombinacionK rk = (RecombinacionK) r;

            return "K" + rk.getK();
        }
    }
}
