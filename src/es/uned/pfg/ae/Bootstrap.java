package es.uned.pfg.ae;

import java.util.Arrays;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionSchwefel;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionNoOp;
import es.uned.pfg.ae.mutacion.MutacionNormal;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionAritmeticaCompleta;
import es.uned.pfg.ae.recombinacion.RecombinacionAritmeticaSimple;
import es.uned.pfg.ae.recombinacion.RecombinacionNoOp;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionNoOp;
import es.uned.pfg.ae.seleccion.SeleccionTorneo;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Bootstrap {

//	private static Aleatorio aleatorio = new Aleatorio(1425107445324L);
	private static Aleatorio aleatorio = new Aleatorio();
	
	public static void main(String[] args) {
		Funcion f = new FuncionSchwefel(2);
		
//		Seleccion seleccion = new SeleccionNoOp();
		Seleccion seleccion = new SeleccionTorneo(3, aleatorio);

//		Recombinacion recombinacion = new RecombinacionNoOp();
		Recombinacion recombinacion = new RecombinacionAritmeticaCompleta(f, 0.4);
//		Recombinacion recombinacion = new RecombinacionAritmeticaSimple(aleatorio, f);
		
//		Mutacion mutacion = new MutacionUniforme(f.getMin(), f.getMax(), aleatorio,
//												 0.01);
		
		Mutacion mutacion = new MutacionNormal(10, aleatorio, f.getMin(), f.getMax());
//		Mutacion mutacion = new MutacionNoOp();
		
		Configuracion conf = new Configuracion();
		
		Individuo[] individuos = getIndividuosInicial(500, f);
//		Arrays.sort(individuos);
		
//		int[][] count = new int[individuos.length][individuos.length];
//		
//		for (int i = 0; i < 1000; i++) {
//			int pos1 = aleatorio.getPosicion(individuos);
//			int pos2 = aleatorio.getPosicion(individuos);
//			
//			count[pos1][pos2]++;
//			
//			System.out.println("Elegi aleatoriamente a " + pos1 + ", " + pos2);
//		}
//		
//		for (int i = 0; i < count.length; i++) {
//			for (int j = 0; j < count.length; j++) {
//				System.out.println(i + ", " + j + " " + count[i][j]);
//			}
//		}
		
		
		System.out.print("===   ANTES   == ");
		System.out.println(Utils.toString(individuos));
		
		Poblacion poblacion = new PoblacionGeneracional(individuos, true);
		
		Terminacion terminacion = new Terminacion() {
			public boolean isTerminado(int iteracion, Poblacion p) {
				return false;
			}
		};
		
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(conf, poblacion, seleccion, 
													 recombinacion, mutacion, 
													 terminacion);

		for (int i = 1; i < 251; i++) {
			ag.iteracion(i);
	
			if (i % 10 == 0) {
				individuos = poblacion.getIndividuos().clone();
				
				System.out.print("===  ITER " + String.format("%3d", i) + " == ");
				Arrays.sort(individuos);
				System.out.println(Utils.toString(individuos));
			}
		}
	}
	
	public static Individuo[] getIndividuosInicial(int tamaño, Funcion f) {
		int dimension = f.getDimension();
		
		Individuo[] individuos = new Individuo[tamaño];
		
		for (int i = 0; i < individuos.length; i++) {
			double[] valores = new double[dimension];
			
			for (int j = 0; j < valores.length; j++) {
				valores[j] = aleatorio.getEntre(f.getMin(), f.getMax());
			}
			
			individuos[i] = new Individuo(i, valores, f);
		}
		
		return individuos;
	}
}
