package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.utils.MultiMap;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class BaseHandler extends AbstractHandler {

    private Map<String, Recurso> recursos = new HashMap<String, Recurso>();
    private Configuracion config;


    public BaseHandler(Configuracion config) {
        this.config = config;

        //TODO completar recursos REST
    }

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        if (target.startsWith("/doc")) {
            return;
        }

        Recurso recurso = recursos.get(target);
        String metodo = baseRequest.getMethod();

        if (recurso != null && metodo.equalsIgnoreCase("GET")) {
            Map<String, String[]> parametros = request.getParameterMap();

            String respuesta = recurso.get(new MultiMap(parametros));

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(respuesta);

            baseRequest.setHandled(true);
        }
    }
}
