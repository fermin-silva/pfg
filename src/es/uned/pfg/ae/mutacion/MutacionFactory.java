package es.uned.pfg.ae.mutacion;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
@SuppressWarnings("serial")
public class MutacionFactory {

	private static final Map<String, Mutacion> MUTACIONES =
			new HashMap<String, Mutacion>() 
	{{
		put(    "NoOp", new MutacionNoOp());
		put(  "Normal", new MutacionNormal());
		put("Uniforme", new MutacionUniforme());
	}};
	
	
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
	
	public static String getNombres() {
		return MUTACIONES.keySet().toString();
	}
}

