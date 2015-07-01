package es.uned.pfg.ae.chart;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.recombinacion.Recombinacion;

import java.util.List;

/**
 * Clase utilitaria que genera los graficos de fitness y convergencia.
 *
 * @author Fermin Silva
 */
public class BasePlot {

    private GraficadorCurvas graficadorFitness;
    private GraficadorCurvas graficadorConvergencia;

    protected String nombre;


    public BasePlot(Funcion f, int maxGeneraciones) {
        this(f.toString().replace("F_", ""), maxGeneraciones);
    }

    public BasePlot(String nombre, int maxGeneraciones) {
        this.nombre = nombre;

        graficadorFitness = new GraficadorCurvas("Curva Progreso " + nombre,
                                                 "Fitness",
                                                 maxGeneraciones);

        graficadorConvergencia = new GraficadorCurvas("Convergencia " + nombre,
                                                      "Desviacion",
                                                      maxGeneraciones);
    }

    /**
     * Indica si los graficos deben contener la leyenda o no
     */
    public void setMostrarLeyenda(boolean mostrarLeyenda) {
        graficadorFitness.setMostrarLeyenda(mostrarLeyenda);
        graficadorConvergencia.setMostrarLeyenda(mostrarLeyenda);
    }

    public void agregar(String nombre, List<Double> curvaProgreso,
                        List<Double> curvaConvergencia)
    {
        graficadorFitness.agregar(nombre, curvaProgreso);
        graficadorConvergencia.agregar(nombre, curvaConvergencia);
    }

    /**
     * Salva ambos graficos en formato de imagen en el disco duro
     * @param nombre ruta completa del archivo a salvar, incluido el nombre
     *               del fichero
     */
    public void guardar(String nombre, int ancho, int alto) {
        graficadorConvergencia.guardar(nombre + "_momento.png", ancho, alto);
        graficadorFitness.guardar(nombre + "_progreso.png", ancho, alto);
    }

    public void guardar(int ancho, int alto) {
        guardar("./" + this.nombre, ancho, alto);
    }

    /**
     * Obtiene un nombre amigable para una recombinacion determinada
     */
    public String getNombre(Recombinacion r) {
        return r.getClass().getSimpleName().replace("Recombinacion", "");
    }
}
