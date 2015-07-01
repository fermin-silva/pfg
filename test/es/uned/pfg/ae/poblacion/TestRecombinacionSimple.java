package es.uned.pfg.ae.poblacion;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionSchwefel;
import es.uned.pfg.ae.recombinacion.RecombinacionSimple;
import es.uned.pfg.ae.utils.Aleatorio;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Prueba el funcionamiento de la recombinacion simple con una configuracion
 * y variables controladas y conocidas con anterioridad
 */
public class TestRecombinacionSimple {

	@Test
	public void test() {
		Aleatorio a = new AleatorioMock();
		Funcion f = new FuncionSchwefel();
		f.setDimension(4);

		//                                           >> | << ese es el punto de cruce
		Individuo i1 = new Individuo(new double[] { 1, 2, 3, 4 }, f);
		Individuo i2 = new Individuo(new double[] { 5, 6, 7, 8 }, f);
		
		RecombinacionSimple recombinacion = new RecombinacionSimple();
		recombinacion.setAlpha(0.5);
		recombinacion.set(a);
		recombinacion.setFuncion(f);
		
		Individuo[] crias = recombinacion.getCrias(i1, i2);
		
		Individuo i11 = crias[0];
		Individuo i22 = crias[1];

		System.out.println(Arrays.toString(i11.getValores()));
		System.out.println(Arrays.toString(i22.getValores()));

		//se espera que los primeros dos genes sean los correspondientes a
		//su respectivo padre, y el resto el promedio entre ambos
		Assert.assertArrayEquals(i11.getValores(), new double[] { 1, 2, 5, 6}, 0.1);
		Assert.assertArrayEquals(i22.getValores(), new double[] { 5, 6, 5, 6}, 0.1);
	}

	//este generador de numeros aleatorios devuelve siempre 1, para poder asi
	//controlar el resultado de la ejecucion y poder realizar afirmaciones
	private static class AleatorioMock extends Aleatorio {

		@Override
		public int getInt(int n) {
			return 1;
		}
	}
}
