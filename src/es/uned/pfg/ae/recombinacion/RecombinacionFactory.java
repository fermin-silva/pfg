package es.uned.pfg.ae.recombinacion;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.funcion.FuncionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Factoria que crea instancias de los operadores de recombinacion segun la
 * configuracion de entrada.
 *
 * @author Fermin Silva
 */
@SuppressWarnings("serial")
public class RecombinacionFactory {

	/**
	 * Mapa con todas las recombinaciones implementadas y el nombre asignado a
	 * ellas. Este nombre se utiliza al teclear los parametros de entrada.
	 * Si se agrega un operador de recombinacion nuevo, este debe agregarse en
	 * este mapa.
	 */
	private static final Map<String, Recombinacion> RECOMBINACIONES =
			new HashMap<String, Recombinacion>()
	{{
		put(              "NoOp", new RecombinacionNoOp());
		put("AritmeticaCompleta", new RecombinacionAritmeticaCompleta());
		put(                 "K", new RecombinacionK());
		put(         "AlphaBeta", new RecombinacionAlphaBeta());
		put(            "Simple", new RecombinacionSimple());
		put(             "Unica", new RecombinacionUnica());
		put(       "DiscretaBGA", new RecombinacionDiscretaBGA());
		put(           "UnPunto", new RecombinacionUnPunto());
	}};

	/**
	 * Crea la recombinacion especificada en la configuracion y le asigna todos
	 * los parametros necesarios
	 */
	public static Recombinacion crear(Configuracion conf) {
		return crear(conf, conf.getRecombinacion());
	}

	/**
	 * Crea la recombinacion cuyo nombre coincide con el parametro. Luego le
	 * asigna todos los parametros necesarios tomandolos de la configuracion
	 */
	public static Recombinacion crear(Configuracion conf, String nombre) {
		Recombinacion recombinacion = RECOMBINACIONES.get(nombre);
		
		if (recombinacion == null) {
			throw new RuntimeException("La recombinacion '" + nombre + 
										"' no fue encontrada");
		}
		
		recombinacion.set(Configuracion.ALEATORIO);

		if (!conf.isBenchmark()) {
			recombinacion.setFuncion(FuncionFactory.crear(conf));
		}

		if (recombinacion instanceof RecombinacionAlpha) {
			((RecombinacionAlpha)recombinacion).setAlpha(conf.getAlpha());
		}
		
		if (recombinacion instanceof RecombinacionAlphaBeta) {
			((RecombinacionAlphaBeta)recombinacion).setBeta(conf.getBeta());
		}
		
		if (recombinacion.getClass().equals(RecombinacionK.class)) {
			((RecombinacionK)recombinacion).setK(conf.getK());
		}
		
		return recombinacion;
	}

	/**
	 * Crea un arreglo con todas las recombinaciones disponibles para realizar
	 * una evaluacion comparativa (benchmark), exceptuando la recombinacion NoOp
	 */
	public static Recombinacion[] crearBenchmark(Configuracion conf) {
		Recombinacion[] r = new Recombinacion[RECOMBINACIONES.size() - 1];
		
		int i = 0;
		for (String nombre : RECOMBINACIONES.keySet()) {
			if (nombre.equals("NoOp")) {
				continue;
			}

			r[i++] = crear(conf, nombre);
		}
		
		return r;
	}

	/**
	 * Devuelve el nombre de todas las recombinaciones disponbiles
	 */
	public static String getNombres() {
		return RECOMBINACIONES.keySet().toString();
	}
}
