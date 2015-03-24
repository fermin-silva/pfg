package es.uned.pfg.ae.poblacion;

import org.junit.Assert;
import org.junit.Test;

import es.uned.pfg.ae.Individuo;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class TestPoblacion {

	@Test
	public void testMejorIndividuo() {
		Individuo i1 = new MockIndividuo(0);
		Individuo i2 = new MockIndividuo(5);
		Individuo i3 = new MockIndividuo(10);
		Individuo i4 = new MockIndividuo(15);
		Individuo i5 = new MockIndividuo(20);
		
		Individuo[] is = new Individuo[]{ i1, i2, i3 ,i4, i5 };
		
		MockPoblacion mockPoblacion = new MockPoblacion(is);
		
		Assert.assertTrue(i5 == mockPoblacion.getMejorIndividuo());
	}
	
	
	/**
	 * Clase individuo 'falsa' solo para propositos de probar el codigo
	 */
	private static class MockIndividuo extends Individuo {
		public MockIndividuo(double fitness) {
			super.fitness = fitness;
		}
	}
	
	/**
	 * Clase poblacion 'falsa' solo para propositos de probar el codigo 
	 */
	private static class MockPoblacion extends Poblacion {

		public MockPoblacion(Individuo[] individuos) {
			super(individuos);
		}

		@Override
		public void setSobrevivientes(Individuo[] crias) {
			
		}
	}
}

