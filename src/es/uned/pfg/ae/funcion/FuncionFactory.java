package es.uned.pfg.ae.funcion;

import es.uned.pfg.ae.Configuracion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Factoria que crea instancias de las funciones segun la configuracion de
 * entrada.
 *
 * @author Fermin Silva
 */
@SuppressWarnings("serial")
public class FuncionFactory {

	/**
	 * Mapa con todas las funciones implementadas y el nombre asignado a ellas.
	 * Este nombre se utiliza al teclear los parametros de entrada.
	 * Si se agrega una funcion nueva, esta debe agregarse en este mapa.
	 */
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

	/**
	 * Crea la funcion especificada en la configuracion y le asigna todos los
	 * parametros necesarios
	 */
	public static Funcion crear(Configuracion conf) {
		return crear(conf, conf.getFuncion());
	}

	/**
	 * Crea la funcion cuyo nombre coincide con el parametro. Luego le asigna
	 * todos los parametros necesarios tomandolos de la configuracion
	 */
	public static Funcion crear(Configuracion conf, String nombre) {
		Funcion funcion = FUNCIONES.get(nombre);
		
		if (funcion == null) {
			throw new RuntimeException("La funcion '" + nombre + 
										"' no fue encontrada");
		}
		
		funcion.setDimension(conf.getDimension());
		
		return funcion;
	}

	/**
	 * Crea un arreglo con todas las funciones disponibles para realizar una
	 * evaluacion comparativa (benchmark). Si la configuracion especifica una
	 * funcion determinada, se devuelve un arreglo con solo esta funcion.
	 */
	public static Funcion[] crearBenchmark(Configuracion conf) {
		Funcion[] funciones = null;

		if (conf.getFuncionNoDefault() != null) {
			Funcion f = crear(conf, conf.getFuncion());

			funciones = new Funcion[]{ f };
		}
		else {
			funciones = new Funcion[FUNCIONES.size()];

			int i = 0;
			for (String nombre : FUNCIONES.keySet()) {
				funciones[i++] = crear(conf, nombre);
			}
		}

		return funciones;
	}

	/**
	 * Devuelve el nombre de todas las funciones disponbiles
	 */
	public static String getNombres() {
		return FUNCIONES.keySet().toString();
	}
}

