package es.uned.pfg.ae.funcion;

import es.uned.pfg.ae.Configuracion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
@SuppressWarnings("serial")
public class FuncionFactory {

	private static final Map<String, Funcion> FUNCIONES =
			new LinkedHashMap<String, Funcion>()
	{{
		put(   "Ackley", new FuncionAckley());
		put( "Griewank", new FuncionGriewank());
		put("Rastrigin", new FuncionRastrigin());
		put("Schaffer2", new FuncionSchaffer2());
		put( "Schubert", new FuncionSchubert());
		put( "Schwefel", new FuncionSchwefel());
	}};
	
	
	public static Funcion crear(Configuracion conf) {
		return crear(conf, conf.getFuncion());
	}
	
	public static Funcion crear(Configuracion conf, String nombre) {
		Funcion funcion = FUNCIONES.get(nombre);
		
		if (funcion == null) {
			throw new RuntimeException("La funcion '" + nombre + 
										"' no fue encontrada");
		}
		
		funcion.setDimension(conf.getDimension());
		
		return funcion;
	}
	
	public static Funcion[] crearBenchmark(Configuracion conf) {
		Funcion[] funciones = new Funcion[FUNCIONES.size()];
		
		int i = 0;
		for (String nombre : FUNCIONES.keySet()) {
			funciones[i++] = crear(conf, nombre);
		}
		
		return funciones;
	}
	
	public static String getNombres() {
		return FUNCIONES.keySet().toString();
	}
}

