package es.uned.pfg.ae.mutacion;

import java.util.HashMap;
import java.util.Map;

import es.uned.pfg.ae.Configuracion;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionFactory;

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
		
		Funcion funcion = FuncionFactory.crear(conf);
		
		mutacion.setMin(funcion.getMin());
		mutacion.setMax(funcion.getMax());
		
		if (mutacion instanceof MutacionNormal) {
			((MutacionNormal)mutacion).setDesviacion(conf.getDesviacionMutacion());
		}
		else if (mutacion instanceof MutacionUniforme) {
			((MutacionUniforme)mutacion).setProb(conf.getProbMutacion());
		}
		
		return mutacion;
	}
	
	public static String getNombres() {
		return MUTACIONES.keySet().toString();
	}
}

