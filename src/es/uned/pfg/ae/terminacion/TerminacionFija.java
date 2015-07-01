package es.uned.pfg.ae.terminacion;

import es.uned.pfg.ae.poblacion.Poblacion;

/**
 * Implementacion del criterio de terminacion que acaba la ejecucion cuando
 * se cumple una cantidad de iteraciones fijada de antemano.
 *
 * @author Fermin Silva
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
