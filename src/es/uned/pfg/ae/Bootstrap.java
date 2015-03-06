package es.uned.pfg.ae;

import java.util.Arrays;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionSchwefel;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionNormal;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionNoOp;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionNoOp;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Bootstrap {

	private static Aleatorio aleatorio = new Aleatorio(1425607445324L); 
	
	public static void main(String[] args) {
		Seleccion seleccion = new SeleccionNoOp();
		
		Recombinacion recombinacion = new RecombinacionNoOp();
		
		Funcion f = new FuncionSchwefel(2);
		
//		Mutacion mutacion = new MutacionUniforme(f.getMin(), f.getMax(), aleatorio,
//												 0.01);
		
		Mutacion mutacion = new MutacionNormal(10, aleatorio, f.getMin(), f.getMax());
		
		
		Configuracion conf = new Configuracion();
		
		Individuo[] individuos = getIndividuosInicial(10, f);
		Arrays.sort(individuos);
		
		System.out.print("===  ANTES  == ");
		System.out.println(Utils.toString(individuos));
		
		Poblacion poblacion = new PoblacionGeneracional(individuos);
		
		Terminacion terminacion = new Terminacion() {
			public boolean isTerminado(int iteracion, Poblacion p) {
				return false;
			}
		};
		
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(conf, poblacion, seleccion, 
													 recombinacion, mutacion, 
													 terminacion);

		for (int i = 1; i < 100; i++) {
			ag.iteracion(i);
	
			System.out.print("===  ITER " + i + " == ");
			Arrays.sort(individuos);
			System.out.println(Utils.toString(individuos));
		}
	}
	
	public static Individuo[] getIndividuosInicial(int tamaño, Funcion f) {
		int dimension = 2;
		
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
