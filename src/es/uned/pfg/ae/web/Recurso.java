package es.uned.pfg.ae.web;

import es.uned.pfg.ae.utils.MultiMap;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public abstract class Recurso {

    public abstract String get(MultiMap multiMap);
}
