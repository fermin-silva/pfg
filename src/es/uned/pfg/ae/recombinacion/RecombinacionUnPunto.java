package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

import java.util.Arrays;

/**
 * Recombinacion Discreta de un punto (one point crossover).
 * Se elige un punto de cruce. Hasta ese punto cada cria tiene los genes del
 * padre correspondiente. A partir de ese punto se intercambian los genes del
 * otro padre.
 *
 * @author Fermin Silva
 */
public class RecombinacionUnPunto implements Recombinacion {

    private Aleatorio aleatorio;
    private Funcion funcion;

    public RecombinacionUnPunto() {

    }

    @Override
    public Individuo[] getCrias(Individuo i1, Individuo i2) {
        double[] x = i1.getValores();
        double[] y = i2.getValores();

        int n = x.length;

        double[] x2 = new double[n];
        double[] y2 = new double[n];

        int punto = aleatorio.getInt(n); //punto de cruce

        //hasta el punto de cruce cada uno de su padre correspondiente
        for (int i = 0; i < punto; i++) {
            x2[i] = x[i];
            y2[i] = y[i];
        }

        //desde el punto hasta el final del arreglo tomar del otro padre
        for (int i = punto; i < n; i++) {
            x2[i] = y[i];
            y2[i] = x[i];
        }

        return new Individuo[] { new Individuo(x2, funcion),
                                 new Individuo(y2, funcion) };
    }

    @Override
    public void set(Aleatorio aleatorio) {
        this.aleatorio = aleatorio;
    }

    @Override
    public void setFuncion(Funcion f) {
        this.funcion = f;
    }

    @Override
    public String toString() {
        return "RecombinacionUnPunto";
    }
}
