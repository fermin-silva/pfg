package es.uned.pfg.ae.utils;


public class Utils {

	private static final int MAX_SIZE_SHORT_STRING = 5;
	private static final String NL = System.getProperty("line.separator");
	
	
	public static double clamp(double d, double min, double max) {
		if (d < min) return min;
		if (d > max) return max;
		
		return d;
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
			}
				
			sb.append(toString(d[i]));
		}
	
		if (d.length > MAX_SIZE_SHORT_STRING) {
			sb.append(" ... ");
		}
		else {
			sb.append(", ");
		}
		
		sb.append(toString(d[d.length - 1])).append("]");
		
		return sb.toString();
	}
	
	public static String toString(double d) {
		return (d > 0 ? "+" : "") + String.format("%.3f", d);
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
}
