package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class Servidor {

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
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase("./web/");
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });

        BaseHandler baseHandler = new BaseHandler(config);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{ resourceHandler, baseHandler });

        server.setHandler(handlers);
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