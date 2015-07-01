package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.utils.MultiMap;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador hecho por el alumno para encaminar pedidos segun su direccion URL
 * a distintos recuros, que deben implementar la interfaz Recurso.
 *
 * @author Fermin Silva
 */
public class BaseHandler extends AbstractHandler {

    /** mapa de direccion URL a instancia de recurso */
    private Map<String, Recurso> recursos = new HashMap<String, Recurso>();


    public BaseHandler(Configuracion config, String imgPath, String webPath) {

        //TODO completar con mas recursos REST
        recursos.put("/api/ag", new RecursoAG(config, imgPath, webPath));
        recursos.put("/host.js", new RecursoHost(config, imgPath, webPath));
    }

    //maneja el pedido HTTP
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
            // obtener los parametros de la query string
            // (formato ?clave=valor&otraClave=valor)
            Map<String, String[]> parametros = request.getParameterMap();

            //se agrega este header (cabecera HTTP) para permitir que el
            //codigo javascript de un host pueda acceder a los recursos
            //ubicados en otro host (lo que se conoce como cross domain)
            //necesario para poder compartir el acceso a la aplicacion
            response.setHeader("Access-Control-Allow-Origin", "*");

            try {
                //obtener la respuesta del recurso
                String respuesta = recurso.get(new MultiMap(parametros));

                //agregar algunas cabeceras HTTP necesarias
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);

                //enviar la respuesta al navegador
                response.getWriter().println(respuesta);
            }
            catch (Exception e) {
                //agregar la cabecera de error interno del servidor con formato
                //texto plano, en vez de json
                response.setContentType("text/plain;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                //imprimir el stacktrace en una variable y luego enviarla al
                //navegador del usuario
                StringWriter stack = new StringWriter();
                e.printStackTrace(new PrintWriter(stack));
                response.getWriter().println(stack.toString());
            }

            //se marca el pedido como manejado para que jetty no intente con
            //el siguiente manejador. si no hay mas manejadores y la respuesta
            //no se ha marcado como procesada, devuelve un error 404
            baseRequest.setHandled(true);
        }
    }
}
