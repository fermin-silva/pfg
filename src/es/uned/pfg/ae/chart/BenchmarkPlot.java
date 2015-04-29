package es.uned.pfg.ae.chart;

import es.uned.pfg.ae.ResultadoBenchmark;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.utils.Utils;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class BenchmarkPlot extends BasePlot {

    private BoxPlot boxPlot;

    public BenchmarkPlot(Funcion f, int maxGeneraciones) {
        super(f, maxGeneraciones);

        super.nombre += "_benchmark";
        this.boxPlot = new BoxPlot();
    }

    public void agregar(ResultadoBenchmark resultado) {
        Recombinacion recombinacion = resultado.getRecombinacion();

        boxPlot.agregar(resultado);

        super.agregar(recombinacion.toString(),
                      Utils.avg(resultado.getProgresos()),
                      Utils.avg(resultado.getMomentosInercia()));
    }

    public void guardar(int ancho, int alto) {
        super.guardar(ancho, alto);

        boxPlot.guardar(nombre + "_boxplot.png", ancho, alto);
    }
}
