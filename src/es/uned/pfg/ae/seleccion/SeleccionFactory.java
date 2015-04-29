package es.uned.pfg.ae.seleccion;

import es.uned.pfg.ae.Configuracion;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
@SuppressWarnings("serial")
public class SeleccionFactory {

	private static final Map<String, Seleccion> SELECCIONES =
			new HashMap<String, Seleccion>() 
	{{
		put(       "NoOp", new SeleccionNoOp());
		put(     "Torneo", new SeleccionTorneo());
		put("Estocastica", new SeleccionEstocasticaUniversal());
	}};
	
	
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
	
	public static String getNombres() {
		return SELECCIONES.keySet().toString();
	}
}

