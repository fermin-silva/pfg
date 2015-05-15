package es.uned.pfg.ae;

import es.uned.pfg.ae.params.Parametros;
import es.uned.pfg.ae.utils.Aleatorio;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {

    public static final String PROP_FUNCION = "funcion";
    public static final String PROP_PROB_MUT = "probabilidad.mutacion";
    public static final String PROP_PROB_RECOMB = "probabilidad.recombinacion";
    public static final String PROP_DESVIACION_MUT = "desviacion.mutacion";
    public static final String PROP_PUNTOS_COMBINACION = "puntos.combinacion";
    public static final String PROP_ALPHA = "alpha";
    public static final String PROP_BETA = "beta";
    public static final String PROP_ELITISMO = "elitismo";
    public static final String PROP_DIMENSION_FUNC = "dimension.funcion";
    public static final String PROP_MUTACION = "mutacion";
    public static final String PROP_SELECCION = "seleccion";
    public static final String PROP_RECOMBINACION = "recombinacion";
    public static final String PROP_TAMAÑO_TORNEO = "tamaño.torneo";
    public static final String PROP_GENERACIONES = "generaciones";
    public static final String PROP_TAMAÑO_POBL = "tamaño.poblacion";
    public static final String PROP_BENCHMARK = "benchmark";

    public static final Aleatorio ALEATORIO =
            new Aleatorio();
//												new Aleatorio(1425117445324L);


    private Parametros params;
    private Properties properties = new Properties();

    //TODO validar tambien el archivo de properties

    public Configuracion(Parametros params) {
        this.params = params;

        if (params.properties != null) {
            try {
                properties.load(new FileInputStream(params.properties));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getTamañoPoblacion() {
        return getInt(params.tamañoPoblacion, PROP_TAMAÑO_POBL, 500);
    }

    public int getGeneraciones() {
        return getInt(params.generaciones, PROP_GENERACIONES, 500);
    }

    public int getTamañoTorneo() {
        return getInt(params.tamañoTorneo, PROP_TAMAÑO_TORNEO, 2);
    }

    public String getSeleccion() {
        return getString(params.seleccion, PROP_SELECCION, "NoOp");
    }

    public String getRecombinacion() {
        return getString(params.recombinacion, PROP_RECOMBINACION, "NoOp");
    }

    public String getMutacion() {
        return getString(params.mutacion, PROP_MUTACION, "NoOp");
    }

    public int getDimension() {
        return getInt(params.dimension, PROP_DIMENSION_FUNC, 2);
    }

    public boolean getElitismo() {
        return getBool(params.elitismo, PROP_ELITISMO, true);
    }

    public boolean getBenchmark() {
        return getBool(params.benchmark, PROP_BENCHMARK, false);
    }

    public double getAlpha() {
        return getDouble(params.alpha, PROP_ALPHA, 0.2);
    }

    public double getBeta() {
        return getDouble(params.beta, PROP_BETA, 0.1);
    }

    public int getK() {
        return getInt(params.puntosCombinacion, PROP_PUNTOS_COMBINACION, 2);
    }

    public double getDesviacionMutacion() {
        return getDouble(params.desviacionMutacion, PROP_DESVIACION_MUT, 0.1);
    }

    public double getProbRecombinacion() {
        return getDouble(params.probRecombinacion, PROP_PROB_RECOMB, 0.5);
    }

    public double getProbMutacion() {
        return getDouble(params.probMutacion, PROP_PROB_MUT, 0.001);
    }

    public String getFuncion() {
        return getString(params.funcion, PROP_FUNCION, "Schwefel");
    }


    /**
     * Si el entero no es nulo, lo devuelve, sino intenta buscar en las
     * properties, y si ahi tambien es nulo, devuelve el valor por defecto.
     */
    protected int getInt(Integer i, String clave, int defecto) {
        if (i != null) {
            return i;
        }

        String property = properties.getProperty(clave);
        if (property != null) {
            return Integer.parseInt(property);
        }

        return defecto;
    }

    /**
     * Si el booleano no es nulo, lo devuelve, sino intenta buscar en las
     * properties, y si ahi tambien es nulo, devuelve el valor por defecto.
     */
    protected boolean getBool(Boolean b, String clave, boolean defecto) {
        if (b != null) {
            return b;
        }

        String property = properties.getProperty(clave);
        if (property != null) {
            return Boolean.parseBoolean(property);
        }

        return defecto;
    }

    /**
     * Si el double no es nulo, lo devuelve, sino intenta buscar en las
     * properties, y si ahi tambien es nulo, devuelve el valor por defecto.
     */
    protected double getDouble(Double d, String clave, double defecto) {
        if (d != null) {
            return d;
        }

        String property = properties.getProperty(clave);
        if (property != null) {
            return Double.parseDouble(property);
        }

        return defecto;
    }

    /**
     * Si el double no es nulo, lo devuelve, sino intenta buscar en las
     * properties, y si ahi tambien es nulo, devuelve el valor por defecto.
     */
    protected String getString(String s, String clave, String defecto) {
        if (s != null) {
            return s;
        }

        String property = properties.getProperty(clave);
        if (property != null) {
            return property.trim();
        }

        return defecto;
    }
}
