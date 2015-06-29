package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * Recombinacion discreta que decide aleatoriamente de que padre elegir cada
 * gen o alelo.
 *
 * Obtenida del paper <br>
 * Muhlenbein & Schlierkamp-Voosen, 1993] Muhlenbein, H. & Schlierkamp-Voosen,
 * D. (1993). <br>
 * Predictive models for the breeder genetic algorithm I. Continuous parameter
 * optimization (pagina 4)
 *
 * @author Fermin Silva < fermins@olx.com >
 */
public class RecombinacionDiscretaBGA implements Recombinacion {

    public static final double PROBABILIDAD = 0.5;

    private Aleatorio aleatorio;
    private Funcion funcion;

    public RecombinacionDiscretaBGA() {

    }

    @Override
    public Individuo[] getCrias(Individuo i1, Individuo i2) {
        double[] x = i1.getValores();
        double[] y = i2.getValores();

        int n = x.length;

        double[] x2 = new double[n];
        double[] y2 = new double[n];

        for (int i = 0; i < n; i++) {
            if (aleatorio.isMenorQue(PROBABILIDAD)) {
                x2[i] = x[i];
                y2[i] = y[i];
            }
            else {
                x2[i] = y[i];
                y2[i] = x[i];
            }
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
        return "RecombinacionDiscretaBGA";
    }
}
