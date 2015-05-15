package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

import java.io.File;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class Servidor {

    public static final String DIR_BASE = "./web/";
    public static final String TMP = "tmp/";


    private Configuracion config;
    private Server server;

    public Servidor(Configuracion config) {
        this.config = config;

        server = new Server();

        //TODO configurar puerto
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8080);
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

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase(".");
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });

        BaseHandler baseHandler = new BaseHandler(config, DIR_BASE + TMP);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, baseHandler});

        server.setHandler(handlers);
    }

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

    public void start() {
        try {
            server.start();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void join() {
        try {
            server.join();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        new Servidor(null).start();
    }
}