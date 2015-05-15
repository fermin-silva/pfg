package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.utils.MultiMap;
import org.eclipse.jetty.util.ajax.JSON;

import java.util.Map;

/**
 * Ejecucion del Algoirtmo Genetico via rest.
 * Devuelve el resultado en JSON
 *
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecursoAG extends Recurso {

    private final String tmpDir;
    private Configuracion config;


    public RecursoAG(Configuracion config, String tmpDir) {
        this.config = config;
        this.tmpDir = tmpDir;
    }

    @Override
    public String get(MultiMap multiMap) {
        Configuracion conf = new ConfiguracionWeb(multiMap);

        Map<String, Object> mapa = new ControladorAG(tmpDir).ejecutar(conf);

        return JSON.toString(mapa);

    }
}
