package es.uned.pfg.ae.chart;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.recombinacion.Recombinacion;

import java.util.List;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class BasePlot {

    private GraficadorCurvas graficadorFitness;
    private GraficadorCurvas graficadorMomento;

    protected String nombre;


    public BasePlot(Funcion f, int maxGeneraciones) {
        this(f.toString().replace("F_", ""), maxGeneraciones);
    }

    public BasePlot(String nombre, int maxGeneraciones) {
        this.nombre = nombre;

        graficadorFitness = new GraficadorCurvas("Curva Progreso " + nombre,
                                                 "Fitness",
                                                 maxGeneraciones);

        graficadorMomento = new GraficadorCurvas("Convergencia " + nombre,
                                                 "Desviacion",
                                                 maxGeneraciones);
    }

    public void setMostrarLeyenda(boolean mostrarLeyenda) {
        graficadorFitness.setMostrarLeyenda(mostrarLeyenda);
        graficadorMomento.setMostrarLeyenda(mostrarLeyenda);
    }

    public void agregar(String nombre, List<Double> curvaProgreso,
                        List<Double> curvaMomentoInercia)
    {
        graficadorFitness.agregar(nombre, curvaProgreso);
        graficadorMomento.agregar(nombre, curvaMomentoInercia);
    }

    public void guardar(int ancho, int alto) {
        graficadorMomento.guardar(nombre + "_momento.png", ancho, alto);
        graficadorFitness.guardar(nombre + "_progreso.png", ancho, alto);
    }

    public String getNombre(Recombinacion r) {
        return r.getClass().getSimpleName().replace("Recombinacion", "");
    }
}
