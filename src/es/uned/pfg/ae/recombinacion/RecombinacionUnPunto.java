package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

import java.util.Arrays;

/**
 * Recombinacion Discreta de un punto (one point crossover)
 * Cada hijo es identico a uno de los padres, excepto por un gen i, que se
 * intercambia entre los padres.
 *
 * @author Fermin Silva < fermins@olx.com >
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

        //se copian los arrays enteros, luego se sobreescriben algunos alelos
        double[] x2 = Arrays.copyOf(x, n);
        double[] y2 = Arrays.copyOf(y, n);

        int i = aleatorio.getInt(n); //punto de cruce

        //intercambiar la i-esima entrada
        double aux = x2[i];
        x2[i] = y2[i];
        y2[i] = aux;

        //TODO cambiarle la iteracion, no es cero!
        return new Individuo[] { new Individuo(0, x2, funcion),
                                 new Individuo(0, y2, funcion) };
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
