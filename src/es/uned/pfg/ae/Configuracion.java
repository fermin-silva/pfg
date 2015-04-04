package es.uned.pfg.ae;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import es.uned.pfg.ae.params.Parametros;
import es.uned.pfg.ae.utils.Aleatorio;

public class Configuracion {

	public static final Aleatorio ALEATORIO_ESTATICO = 
											new Aleatorio(1425117445324L);
	
	public static final Aleatorio ALEATORIO_DEFAULT = new Aleatorio();

	
	private Parametros params;
	private Properties properties = new Properties();
	
	//TODO validar tambien el archivo de properties
	
	public Configuracion(Parametros params) {
		this.params = params;
		
		if (params.properties != null) {
			try {
				properties.load(new FileInputStream(params.properties));
			} 
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public int getTamañoPoblacion() {
		return getInt(params.tamañoPoblacion, "tamaño.poblacion", 500);
	}
	
	public int getGeneraciones() {
		return getInt(params.generaciones, "generaciones", 500);
	}
	
	public int getTamañoTorneo() {
		return getInt(params.tamañoTorneo, "tamaño.torneo", 2);
	}
	
	public int getDimension() {
		return getInt(params.dimension, "dimension.funcion", 2);
	}
	
	public boolean getElitismo() {
		return getBool(params.elitismo, "elitismo", true);
	}
	
	public double getAlpha() {
		return getDouble(params.alpha, "alpha", 0.2);
	}
	
	public int getK() {
		return getInt(params.puntosCombinacion, "puntos.combinacion", 2);
	}
	
	public double getDesviacionMutacion() {
		return getDouble(params.desviacionMutacion, "desviacion.mutacion", 0.1);
	}
	
	public double getProbRecombinacion() {
		return getDouble(params.probRecombinacion, "desviacion.mutacion", 0.5);
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
}
