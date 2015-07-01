package es.uned.pfg.ae;

import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;
import org.junit.Test;

/**
 * Pequeña prueba del rendimiento del generador de numeros aleatorios vs las
 * operaciones matematicas realizadas por la recombinacion aritmetica completa.
 * Sirve para comparar el rendimiento.
 *
 * @author Fermin Silva < fermins@olx.com >
 */
public class TestRendimientoAleatorio {

    public static void main(String[] args) {
        double[] x = new double[10];
        double[] y = new double[10];
        double[] x2 = new double[10];
        double[] y2 = new double[10];
        double alpha = 0.1;
        double min = 100;
        double max = 400;
        double PROBABILIDAD = 0.5;

        Aleatorio aleatorio = new Aleatorio();

        long start = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            for (int i = 0; i < x.length; i++) {
                x2[i] = alpha * x[i] + (1 - alpha) * y[i];
                y2[i] = alpha * y[i] + (1 - alpha) * x[i];

                x2[i] = Utils.clamp(x2[i], min, max);
                y2[i] = Utils.clamp(y2[i], min, max);
            }
        }
        long end = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            for (int i = 0; i < x.length; i++) {
                if (aleatorio.isMenorQue(PROBABILIDAD)) {
                    x2[i] = x[i];
                    y2[i] = y[i];
                }
                else {
                    x2[i] = y[i];
                    y2[i] = x[i];
                }
            }
        }
        long end2 = System.currentTimeMillis();

        System.out.println("Operaciones aritmeticas tardaron tardo: " +
                            (end - start) + "ms");

        System.out.println("Generador numeros aleatorios tardo:  " +
                            (end2 - start2) + "ms");
    }
}
