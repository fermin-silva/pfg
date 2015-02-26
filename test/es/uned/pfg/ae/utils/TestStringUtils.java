package es.uned.pfg.ae.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestStringUtils {

	@Test
	public void testShortString() {
		double[] arr = new double[] { 1.12345, 1.98765, 1.123987, 1.987123, 
									  1.987321, 1.321987 };
		
		String str = StringUtils.toShortString(arr);
		
		Assert.assertEquals("[1.123, 1.988, 1.124, 1.987 ... 1.322]", str);
	}
}
