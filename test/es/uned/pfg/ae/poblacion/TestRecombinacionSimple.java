package es.uned.pfg.ae.poblacion;

import org.junit.Assert;
import org.junit.Test;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionSchwefel;
import es.uned.pfg.ae.recombinacion.RecombinacionSimple;
import es.uned.pfg.ae.utils.Aleatorio;

public class TestRecombinacionSimple {

	@Test
	public void test() {
		Aleatorio a = new AleatorioMock();
		Funcion f = new FuncionSchwefel();
		
		//                                               >> | << ese es el punto de cruce  
		Individuo i1 = new Individuo(0, new double[] { 1, 2, 3, 4 }, f);
		Individuo i2 = new Individuo(0, new double[] { 5, 6, 7, 8 }, f);
		
		RecombinacionSimple recombinacion = new RecombinacionSimple(a, f);
		
		Individuo[] crias = recombinacion.getCrias(i1, i2);
		
		Individuo i11 = crias[0];
		Individuo i22 = crias[1];
		
		Assert.assertArrayEquals(i11.getValores(), new double[] { 1, 2, 7, 8}, 0.1);
		Assert.assertArrayEquals(i22.getValores(), new double[] { 5, 6, 3, 4}, 0.1);
	}
	
	private static class AleatorioMock extends Aleatorio {
		
		@Override
		public int getPosicion(double[] d) {
			return 2;
		}
	}
}
