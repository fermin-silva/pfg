package es.uned.pfg.ae.web;

import es.uned.pfg.ae.AlgoritmoGenetico;
import es.uned.pfg.ae.Benchmark;
import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.chart.BasePlot;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class ControladorAG {

    private final String tmpDir;

    public ControladorAG(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    public Map<String, Object> ejecutar(Configuracion conf) {
        Map<String, Object> mapa = new HashMap<String, Object>();

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

        mapa.put("tiempo", ag.getTiempo());

        graficar(f, conf, ag, mapa);
        agregarPoblacion(poblacion, mapa);

        return mapa;
    }

    protected void agregarPoblacion(Poblacion p, Map<String, Object> mapa) {
        Individuo mejor = p.getMejorIndividuo();

        mapa.put("minimo", mejor.getFitness());
        mapa.put("coords", mejor.getValores());
        mapa.put("centroide", p.getCentroide());
        mapa.put("desviacion", p.getDesviacion());
    }

    protected void graficar(Funcion f, Configuracion conf,
                            AlgoritmoGenetico ag, Map<String, Object> mapa)
    {
        String imgPath = tmpDir + System.currentTimeMillis();

        BasePlot basePlot = new BasePlot(f, conf.getGeneraciones());
        basePlot.setMostrarLeyenda(false);
        basePlot.agregar("nombre", ag.getCurvaProgreso(), ag.getMomentosInercia());
        basePlot.guardar(imgPath, Benchmark.ANCHO, Benchmark.ALTO);

        mapa.put("img.momento", imgPath + "_momento.png");
        mapa.put("img.progreso", imgPath + "_progreso.png");
    }
}
