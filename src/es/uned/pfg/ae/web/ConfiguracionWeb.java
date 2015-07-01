package es.uned.pfg.ae.web;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.params.Parametros;
import es.uned.pfg.ae.utils.MultiMap;

/**
 * Clase de configuracion que emula a su clase padre, pero donde los parametros
 * provienen de un MultiMap via HTTP y no por la linea de comandos
 *
 * @author Fermin Silva
 */
public class ConfiguracionWeb extends Configuracion {

    private MultiMap mapa;


    public ConfiguracionWeb(MultiMap mapa) {
        super(new Parametros()); //parametros vacios;

        this.mapa = mapa;
    }

    @Override
    public int getTamañoPoblacion() {
        return getInt(mapa, PROP_TAMAÑO_POBL, 500);
    }

    @Override
    public int getGeneraciones() {
        return getInt(mapa, PROP_GENERACIONES, 500);
    }

    @Override
    public int getTamañoTorneo() {
        return getInt(mapa, PROP_TAMAÑO_TORNEO, 2);
    }

    @Override
    public String getSeleccion() {
        return getString(mapa, PROP_SELECCION, "NoOp");
    }

    @Override
    public String getRecombinacion() {
        return getString(mapa, PROP_RECOMBINACION, "NoOp");
    }

    @Override
    public String getMutacion() {
        return getString(mapa, PROP_MUTACION, "NoOp");
    }

    @Override
    public int getDimension() {
        return getInt(mapa, PROP_DIMENSION_FUNC, 2);
    }

    @Override
    public boolean getElitismo() {
        return getBool(mapa, PROP_ELITISMO, true);
    }

    @Override
    public boolean isBenchmark() {
        return getBool(mapa, PROP_BENCHMARK, false);
    }

    @Override
    public double getAlpha() {
        return getDouble(mapa, PROP_ALPHA, 0.2);
    }

    @Override
    public double getBeta() {
        return getDouble(mapa, PROP_BETA, 0.1);
    }

    @Override
    public int getK() {
        return getInt(mapa, PROP_PUNTOS_COMBINACION, 2);
    }

    @Override
    public double getDesviacionMutacion() {
        return getDouble(mapa, PROP_DESVIACION_MUT, 0.1);
    }

    @Override
    public double getProbRecombinacion() {
        return getDouble(mapa, PROP_PROB_RECOMB, 0.5);
    }

    @Override
    public double getProbMutacion() {
        return getDouble(mapa, PROP_PROB_MUT, 0.001);
    }

    @Override
    public String getFuncion() {
        return getString(mapa, PROP_FUNCION, "Schwefel");
    }

    protected int getInt(MultiMap mapa, String clave, int defecto) {
        return Integer.parseInt(mapa.getPrimero(clave, String.valueOf(defecto)));
    }

    protected boolean getBool(MultiMap mapa, String clave, boolean defecto) {
        return Boolean.parseBoolean(mapa.getPrimero(clave, String.valueOf(defecto)));
    }

    protected double getDouble(MultiMap mapa, String clave, double defecto) {
        return Double.parseDouble(mapa.getPrimero(clave, String.valueOf(defecto)));
    }

    protected String getString(MultiMap mapa, String clave, String defecto) {
        return mapa.getPrimero(clave, defecto);
    }
}
