package es.uned.pfg.ae.utils;

import java.util.Arrays;

public class StringUtils {

	private static final int MAX_SIZE_SHORT_STRING = 5;
	
	/**
	 * Devuelve una representacion compacta del array
	 */
	public static String toShortString(double[] d) {
		if (d.length > MAX_SIZE_SHORT_STRING) {
			StringBuilder sb = new StringBuilder("[");
			
			for (int i = 0; i < MAX_SIZE_SHORT_STRING - 1; i++) {
				if (i != 0) {
					sb.append(", ");
				}
					
				sb.append(toString(d[i]));
			}
			
			sb.append(" ... ")
			  .append(toString(d[d.length - 1]))
			  .append("]");
			
			return sb.toString();
		}

		return Arrays.toString(d);
	}
	
	public static String toString(double[] d) {
		return String.format("%.3f", d);
	}
	
	public static String toString(double d) {
		return String.format("%.3f", d);
	}
}
