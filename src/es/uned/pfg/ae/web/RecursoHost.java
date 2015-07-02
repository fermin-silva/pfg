package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.utils.MultiMap;

/**
 * Recurso que obtiene la direccion del host con el que debe interactuar
 * el navegador. <br>
 * Devuelve un archivo javascript que es interpretado por el navegador.
 * De querer cambiarse el host donde se encuentra el servidor, puede crearse
 * un archivo host.js con la direccion. Este archivo tendra prioridad por sobre
 * este recurso, por lo que permitira sobrescribir la direccion. <br>
 * Se utiliza cuando la pagina se visualiza desde Github, para indicar que
 * el servidor que ejecuta el algoritmo no es el propio Github sino la
 * direccion IP del servidor proporcionado por el alumno.
 *
 * @author Fermin Silva
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

    //el host es fijo. la / indica el propio host desde donde se esta
    //visualizando la pagina
    @Override
    public String get(MultiMap multiMap) {
        return "var host = ''";
    }
}
