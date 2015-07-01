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
 * Controlador con la logica para ejecutar el algoritmo genetico via Web.
 * El RecursoAG lo invocara para obtener los resultados de la ejecucion y
 * devolverlos al navegador del cliente.
 *
 * @author Fermin Silva
 */
public class ControladorAG {

    private String webDir;
    private String tmpDir;

    public ControladorAG(String tmpDir, String webDir) {
        this.tmpDir = tmpDir;
        this.webDir = webDir;
    }

    /**
     * Ejecuta el algoritmo genetico de manera similar al modo de ejecucion
     * por consola, solo que en vez de imprimir los resultados por pantalla,
     * los agrega a un mapa
     */
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
        agregarPoblacion(poblacion, f, mapa);

        return mapa;
    }

    /**
     * Agrega los datos de la poblacion, su mejor individuo, fitness promedio,
     * etc al mapa con los resultados de la ejecucion.
     */
    protected void agregarPoblacion(Poblacion p, Funcion f,
                                    Map<String, Object> mapa)
    {
        Individuo mejor = p.getMejorIndividuo();

        double minimoGlobal = f.getMinimoGlobal();
        double minimo = mejor.getValorFuncion();

        mapa.put("minimo", minimo);
        mapa.put("minimoGlobal", minimoGlobal);
        mapa.put("mejorFitness", mejor.getFitness());
        mapa.put("coords", mejor.getValores());
        mapa.put("centroide", p.getCentroide());
        mapa.put("desviacion", p.getDesviacion());
    }

    /**
     * Genera los graficos con los resultados de la ejecucion en un
     * directorio temporal. Estos graficos son luego pedidos por el navegador
     * del cliente, por lo que seran servidos por el ResourceHandler de
     * Jetty. <br>
     * Se le asigna un nombre aleatorio a cada uno para permitir ejecucion
     * en simultaneo por parte de varios clientes.
     */
    protected void graficar(Funcion f, Configuracion conf,
                            AlgoritmoGenetico ag, Map<String, Object> mapa)
    {
        //se usa la hora actual como parte del nombre del archivo para
        //garantizar la unicidad de los nombres
        String imgPath = tmpDir + System.currentTimeMillis();

        BasePlot basePlot = new BasePlot(f, conf.getGeneraciones());
        basePlot.setMostrarLeyenda(false);
        basePlot.agregar("nombre", ag.getCurvaProgreso(), ag.getCurvaCovergencia());
        basePlot.guardar(webDir + imgPath, Benchmark.ANCHO, Benchmark.ALTO);

        mapa.put("img.momento", imgPath + "_momento.png");
        mapa.put("img.progreso", imgPath + "_progreso.png");
    }
}
