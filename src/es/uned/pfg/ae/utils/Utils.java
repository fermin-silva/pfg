package es.uned.pfg.ae.utils;


import java.util.ArrayList;
import java.util.List;

public class Utils {

	private static final int MAX_SIZE_SHORT_STRING = 8;
	private static final String NL = System.getProperty("line.separator");
	
	
	public static double clamp(double d, double min, double max) {
		if (d < min) return min;
		if (d > max) return max;
		
		return d;
	}
	
	public static String toShortString(Object[] d) {
		StringBuilder sb = new StringBuilder("[");
		
		for (int i = 0; i < Math.min(MAX_SIZE_SHORT_STRING, d.length) - 1; i++) {
			if (i != 0) {
				sb.append(", ");
			}
				
			sb.append(d[i].toString());
		}
	
		if (d.length > MAX_SIZE_SHORT_STRING) {
			sb.append(" ... ");
		}
		else {
			sb.append(", ");
		}
		
		sb.append(d[d.length - 1].toString()).append("]");
		
		return sb.toString();
	}
	
	public static String toShortString(double[] d) {
		return toShortString(d, false);
	}
	
	/**
	 * Devuelve una representacion compacta del array
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
	
	public static String toString(double d) {
		return String.format("%9s", (d > 0 ? "+" : "") + String.format("%.3f", d));
	}
	
	public static String toString(Object[] o) {
		return toString(o, false);
	}
	
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
