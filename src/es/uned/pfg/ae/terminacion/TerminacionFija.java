package es.uned.pfg.ae.terminacion;

import es.uned.pfg.ae.poblacion.Poblacion;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class TerminacionFija implements Terminacion {

    private int generaciones;

    public TerminacionFija(int generaciones) {
        this.generaciones = generaciones;
    }

    @Override
    public boolean isTerminado(int iteracion, Poblacion p) {
        return iteracion > generaciones;
    }
}
