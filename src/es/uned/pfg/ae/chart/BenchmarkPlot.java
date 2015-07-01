package es.uned.pfg.ae.chart;

import es.uned.pfg.ae.ResultadoBenchmark;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.utils.Utils;

/**
 * Instancia especifica de BasePlot que agrega las graficas de tiempo y boxplot
 *
 * @author Fermin Silva
 */
public class BenchmarkPlot extends BasePlot {

    private GraficadorBoxPlot boxPlot;
    private GraficadorBarras graficadorTiempo;


    public BenchmarkPlot(Funcion f, int maxGeneraciones) {
        super(f, maxGeneraciones);

        super.nombre += "_benchmark";
        String nombreF = f.toString().replace("F_", "");

        this.boxPlot = new GraficadorBoxPlot("Benchmark " + nombreF);

        graficadorTiempo = new GraficadorBarras("Tiempos de Ejecucion " + nombreF,
                                                "Tiempo (s)");
    }

    /**
     * Agrega el resultado de una ejecucion del benchmark (las 100 corridas
     * del algoritmo) a las estructuras de los graficadores de barras y boxplot
     *
     * @param resultado
     */
    public void agregar(ResultadoBenchmark resultado) {
        Recombinacion recombinacion = resultado.getRecombinacion();
        String nombre = getNombre(recombinacion);

        boxPlot.agregar(nombre, resultado);
        graficadorTiempo.agregar(nombre, resultado.getTiempo());

        super.agregar(nombre,
                      Utils.avg(resultado.getProgresos()),
                      Utils.avg(resultado.getMomentosInercia()));
    }

    @Override
    public void guardar(int ancho, int alto) {
        guardar("./" + this.nombre, ancho, alto);
    }

    @Override
    public void guardar(String nombre, int ancho, int alto) {
        super.guardar(nombre, ancho, alto);

        boxPlot.guardar(nombre + "_boxplot.png", ancho, alto);
        graficadorTiempo.guardar(nombre + "_tiempos.png", ancho, alto);
    }
}
