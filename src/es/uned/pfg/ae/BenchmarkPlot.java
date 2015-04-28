package es.uned.pfg.ae;

import es.uned.pfg.ae.chart.BoxPlot;
import es.uned.pfg.ae.chart.GraficadorCurvas;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.utils.Utils;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class BenchmarkPlot {

    private BoxPlot boxPlot;

    private GraficadorCurvas graficadorFitness;
    private GraficadorCurvas graficadorMomento;

    private String nombre;


    public BenchmarkPlot(Funcion f, int maxGeneraciones) {
        this.nombre = f.toString().replace("F_", "");

        boxPlot = new BoxPlot();
        graficadorFitness = new GraficadorCurvas("Curva Progreso " + nombre,
                                                 "Fitness",
                                                 maxGeneraciones);

        graficadorMomento = new GraficadorCurvas("Convergencia " + nombre,
                                                 "Momento Inercia",
                                                 maxGeneraciones);
    }

    public void agregar(ResultadoBenchmark resultado) {
        Recombinacion recombinacion = resultado.getRecombinacion();

        boxPlot.agregar(resultado);
        graficadorFitness.agregar(recombinacion.toString(),
                                  Utils.avg(resultado.getProgresos()));

        graficadorMomento.agregar(recombinacion.toString(),
                                  Utils.avg(resultado.getMomentosInercia()));
    }

    public void guardar(int ancho, int alto) {
        graficadorMomento.guardar(nombre + "_benchmark_momento.png",
                                  ancho, alto);

        graficadorFitness.guardar(nombre + "_benchmark_progreso.png",
                                  ancho, alto);

        boxPlot.guardar(nombre + "_benchmark_boxplot.png",
                        ancho, alto);
    }
}
