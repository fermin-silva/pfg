package es.uned.pfg.ae.mutacion;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Factoria que crea instancias de los operadores de mutacion segun la
 * configuracion de entrada.
 *
 * @author Fermin Silva
 */
@SuppressWarnings("serial")
public class MutacionFactory {

	/**
	 * Mapa con todas las mutaciones implementadas y el nombre asignado a ellas.
	 * Este nombre se utiliza al teclear los parametros de entrada.
	 * Si se agrega un operador de mutacion nuevo, este debe agregarse en este mapa.
	 */
	private static final Map<String, Mutacion> MUTACIONES =
			new HashMap<String, Mutacion>()
			{{
					put(    "NoOp", new MutacionNoOp());
					put(  "Normal", new MutacionNormal());
					put("Uniforme", new MutacionUniforme());
				}};


	/**
	 * Crea la mutacion especificada en la configuracion y le asigna todos los
	 * parametros necesarios
	 */
	public static Mutacion crear(Configuracion conf) {
		String nombre = conf.getMutacion();

		Mutacion mutacion = MUTACIONES.get(nombre);

		if (mutacion == null) {
			throw new RuntimeException("La mutacion '" + nombre +
										"' no fue encontrada");
		}

		mutacion.set(Configuracion.ALEATORIO);

		if (!conf.isBenchmark()) {
			Funcion funcion = FuncionFactory.crear(conf);

			mutacion.setMin(funcion.getMin());
			mutacion.setMax(funcion.getMax());
		}

		mutacion.setProb(conf.getProbMutacion());

		if (mutacion instanceof MutacionNormal) {
			((MutacionNormal)mutacion).setDesviacion(conf.getDesviacionMutacion());
		}

		return mutacion;
	}

	/**
	 * Devuelve el nombre de todas las mutaciones disponbiles
	 */
	public static String getNombres() {
		return MUTACIONES.keySet().toString();
	}
}

