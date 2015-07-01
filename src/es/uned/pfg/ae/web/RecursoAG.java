package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.utils.MultiMap;
import org.eclipse.jetty.util.ajax.JSON;

import javax.xml.ws.WebEndpoint;
import java.util.Map;

/**
 * Recurso que ejecuta el Algoritmo Genetico via Web.
 * Devuelve el resultado en formato JSON
 *
 * @author Fermin Silva
 */
public class RecursoAG extends Recurso {

    private String tmpDir;
    private String webDir;

    private Configuracion config;


    public RecursoAG(Configuracion config, String tmpDir, String webDir) {
        this.config = config;
        this.tmpDir = tmpDir;
        this.webDir = webDir;
    }

    @Override
    public String get(MultiMap multiMap) {
        Configuracion conf = new ConfiguracionWeb(multiMap);

        //se delega la ejecucion a un controlador, ya que el recurso es
        //simplemente el punto de entrada Web, y no deberia contener mucha logica
        Map<String, Object> mapa =
                            new ControladorAG(tmpDir, webDir).ejecutar(conf);

        //convertir el mapa con los resultados de la ejecucion a formato
        //json para que pueda ser interpretado por el navegador del cliente
        return JSON.toString(mapa);
    }
}
