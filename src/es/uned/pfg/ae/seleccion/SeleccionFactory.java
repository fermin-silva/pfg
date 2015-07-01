package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Configuracion;

import java.util.HashMap;
import java.util.Map;

/**
 * Factoria que crea instancias de los operadores de seleccion segun la
 * configuracion de entrada.
 *
 * @author Fermin Silva
 */
@SuppressWarnings("serial")
public class SeleccionFactory {

	/**
	 * Mapa con todas las recombinaciones implementadas y el nombre asignado a
	 * ellas. Este nombre se utiliza al teclear los parametros de entrada.
	 * Si se agrega un operador de recombinacion nuevo, este debe agregarse en
	 * este mapa.
	 */
	private static final Map<String, Seleccion> SELECCIONES =
			new HashMap<String, Seleccion>() 
	{{
		put(       "NoOp", new SeleccionNoOp());
		put(     "Torneo", new SeleccionTorneo());
	}};

	/**
	 * Crea la seleccion especificada en la configuracion y le asigna todos
	 * los parametros necesarios
	 */
	public static Seleccion crear(Configuracion conf) {
		String nombre = conf.getSeleccion();
		
		Seleccion seleccion = SELECCIONES.get(nombre);
		
		if (seleccion == null) {
			throw new RuntimeException("La seleccion '" + nombre + 
										"' no fue encontrada");
		}
		
		seleccion.set(Configuracion.ALEATORIO);
		
		if (seleccion instanceof SeleccionTorneo) {
			((SeleccionTorneo)seleccion).setTamaño(conf.getTamañoTorneo());
		}
		
		return seleccion;
	}

	/**
	 * Devuelve el nombre de todas las selecciones disponbiles
	 */
	public static String getNombres() {
		return SELECCIONES.keySet().toString();
	}
}

