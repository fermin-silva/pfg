package es.uned.pfg.ae.web;

import es.uned.pfg.ae.utils.MultiMap;

/**
 * Clase base para todos los recursos Web.
 *
 * @author Fermin Silva
 */
public abstract class Recurso {

    /**
     * Devuelve una respuesta a un pedido HTTP en base a los parametros
     * enviados desde el navegador
     */
    public abstract String get(MultiMap multiMap);
}
