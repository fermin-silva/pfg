package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.utils.MultiMap;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecursoHost extends Recurso {

    private String tmpDir;
    private String webDir;

    private Configuracion config;


    public RecursoHost(Configuracion config, String tmpDir, String webDir) {
        this.config = config;
        this.tmpDir = tmpDir;
        this.webDir = webDir;
    }

    @Override
    public String get(MultiMap multiMap) {
        return "var host = '/'";
    }
}
