package es.uned.pfg.ae.recombinacion;

import java.util.HashMap;
import java.util.Map;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.funcion.FuncionFactory;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
@SuppressWarnings("serial")
public class RecombinacionFactory {

	private static final Map<String, Recombinacion> RECOMBINACIONES =
			new HashMap<String, Recombinacion>() 
	{{
		put(              "NoOp", new RecombinacionNoOp());
		put("AritmeticaCompleta", new RecombinacionAritmeticaCompleta());
		put(                 "K", new RecombinacionK());
		put(            "Simple", new RecombinacionSimple());
		put(             "Unica", new RecombinacionUnica());
	}};
	
	
	public static Recombinacion crear(Configuracion conf) {
		return crear(conf, conf.getRecombinacion());
	}
	
	public static Recombinacion crear(Configuracion conf, String nombre) {
		Recombinacion recombinacion = RECOMBINACIONES.get(nombre);
		
		if (recombinacion == null) {
			throw new RuntimeException("La recombinacion '" + nombre + 
										"' no fue encontrada");
		}
		
		recombinacion.set(Configuracion.ALEATORIO);
		recombinacion.setFuncion(FuncionFactory.crear(conf));
		
		if (recombinacion instanceof RecombinacionAlpha) {
			((RecombinacionAlpha)recombinacion).setAlpha(conf.getAlpha());
		}
		
		if (recombinacion instanceof RecombinacionK) {
			((RecombinacionK)recombinacion).setK(conf.getK());
		}
		
		return recombinacion;
	}
	
	public static Recombinacion[] crearBenchmark(Configuracion conf) {
		Recombinacion[] r = new Recombinacion[RECOMBINACIONES.size()];
		
		int i = 0;
		for (String nombre : RECOMBINACIONES.keySet()) {
			r[i++] = crear(conf, nombre);
		}
		
		return r;
	}
	
	public static String getNombres() {
		return RECOMBINACIONES.keySet().toString();
	}
}
