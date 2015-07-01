package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

import java.io.File;

/**
 * Clase de alto nivel para el servidor web de Jetty.
 *
 * @author Fermin Silva
 */
public class Servidor {

    public static final String DIR_BASE = "./web/";
    public static final String TMP = "tmp/";


    private Configuracion config;
    private Server server;

    public Servidor(Configuracion config) {
        this.config = config;

        server = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(config.getPuerto());

        server.addConnector(connector);

        agregarHandlers(config);

        try {
            server.start();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void agregarHandlers(Configuracion config) {
        crearDirectorios();

        //el primer manejador sirve archivos estaticos desde el disco duro
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase(DIR_BASE);

        //por defecto al acceder al recurso raiz "/", devolvera el archivo
        //llamado index.html
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });

        //el segundo manejador, hecho por el alumno, sirve para asignar
        //distintas direcciones URL a los distintos recursos Web
        BaseHandler baseHandler = new BaseHandler(config, TMP, DIR_BASE);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, baseHandler});

        server.setHandler(handlers);
    }

    /**
     * Crea el directorio web y tmp, si estos no existen
     */
    private void crearDirectorios() {
        File directorio = new File(DIR_BASE);

        if (!directorio.exists() || !directorio.isDirectory()) {
            directorio.mkdir();
        }

        File tmp = new File(directorio, TMP);

        if (!tmp.exists() || !tmp.isDirectory()) {
            tmp.mkdir();
        }
    }

    /**
     * Arranca el servidor de Jetty
     */
    public void start() {
        try {
            server.start();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Bloquea la ejecucion del hilo principal indefinidamente hasta que se
     * interrumpa la ejecucion del servidor.
     */
    public void join() {
        try {
            server.join();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}