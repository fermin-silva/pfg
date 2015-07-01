package es.uned.pfg.ae.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * Clase con utilidades varias poco cohesivas entre si, por lo que se agrupan
 * en esta clase.
 *
 * @author Fermin Silva
 */
public class Utils {

	/**
	 * Cantidad de cadenas en un arreglo que se imprimen antes de condensar
	 * la salida utilizando '...'.
	 * Si el arreglo tuviese mas elementos, se mostraran los primeros y el
	 * ultimo.
	 */
	private static final int MAX_SIZE_SHORT_STRING = 8;
	private static final String NL = System.getProperty("line.separator");

	/**
	 * Restringe el valor de d entre unos valores minimo y maximo. Si d
	 * estuviese fura de ese intervalo, se mueve el valor a aquel mas cercano.
	 */
	public static double clamp(double d, double min, double max) {
		if (d < min) return min;
		if (d > max) return max;
		
		return d;
	}

	/**
	 * Devuelve una representacion compacta del arreglo de doubles en una misma
	 * linea.
	 */
	public static String toShortString(double[] d) {
		return toShortString(d, false);
	}
	
	/**
	 * Devuelve una representacion compacta del arreglo.
	 * @param newLine indica si deben mostrarse los elementos del arreglo
	 *                en una nueva linea cada uno
	 */
	public static String toShortString(double[] d, boolean newLine) {
		StringBuilder sb = new StringBuilder("[");
		
		for (int i = 0; i < Math.min(MAX_SIZE_SHORT_STRING, d.length) - 1; i++) {
			if (i != 0) {
				sb.append(", ");
				
				if (newLine) {
					sb.append(NL);
				}
			}
				
			sb.append(toString(d[i]));
		}
	
		if (d.length > MAX_SIZE_SHORT_STRING) {
			sb.append(" ... ");
		}
		else {
			sb.append(", ");
		}
		
		if (newLine) {
			sb.append(NL);
		}
		
		sb.append(toString(d[d.length - 1])).append("]");
		
		return sb.toString();
	}

	/**
	 * Devuelve una representacion compacta del valor double
	 */
	public static String toString(double d) {
		return String.format("%9s", (d > 0 ? "+" : "") + String.format("%.3f", d));
	}

	/**
	 * Devuelve una representacion compacta del arreglo de objetos en una
	 * misma linea
	 */
	public static String toString(Object[] o) {
		return toString(o, false);
	}

	/**
	 * Devuelve una representacion compacta del arreglo.
	 * @param newLine indica si deben mostrarse los elementos del arreglo
	 *                en una nueva linea cada uno
	 */
	public static String toString(Object[] o, boolean newLine) {
		StringBuilder sb = new StringBuilder("[");
		
		for (int i = 0; i < o.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			
			if (newLine) {
				sb.append(NL + "  ");
			}
			
			sb.append(o[i].toString());
		}

		if (newLine) {
			sb.append(NL);
		}
		
		return sb.append("]").toString();
	}

	/**
	 * Promedia los valores de las listas que se encuentran en el mismo
	 * indice (promedia el primero con el primero, el segundo con el segundo,
	 * etc). <br>
	 * Devuelve una sola lista con los promedios
	 */
	public static List<Double> avg(List<List<Double>> listas) {
		List<Double> ret = new ArrayList<Double>();

		for (int i = 0; i < listas.get(0).size(); i++) {
			double suma = 0;

			for (List<Double> lista : listas) {
				suma += lista.get(i);
			}

			ret.add(suma / listas.size());
		}

		return ret;
	}
}
