package es.uned.pfg.ae.seleccion;

import java.util.Arrays;

import es.uned.pfg.ae.Individuo;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.utils.Aleatorio;
import es.uned.pfg.ae.utils.Utils;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class SeleccionEstocasticaUniversal implements Seleccion {

	private Aleatorio aleatorio;

	
	public SeleccionEstocasticaUniversal(Aleatorio aleatorio) {
		this.aleatorio = aleatorio;
	}
	
	@Override
	public Individuo[] seleccionar(Poblacion p) {
		if (1 < 2) {
			throw new UnsupportedOperationException("El mejor individuo tiene menos chances porque |fitness| es menor!");
		}
		
		Individuo[] padres = new Individuo[p.getTamaño()];
		int[] selecciones = new int[p.getTamaño()];
		
		int current_member = 0;
		int i = 0;
		
		double delta = 1.0 / p.getTamaño();
		double r = aleatorio.getEntre(0, delta);
		double[] a = getProbSeleccion(p.getIndividuos());
		
//		for (int j = 0; j < a.length; j++) {
//			System.out.println(a[j] * 1000 + "      " + p.getIndividuos()[j]);
//		}
		
		while (current_member < p.getTamaño()) {
			while (r <= a[i]) {
				selecciones[current_member] = i;
				padres[current_member] = p.getIndividuos()[i];
				r = r + delta;
				current_member++;
			}
			
			i++;
		}
		
//		Arrays.sort(selecciones);
//		System.out.println(selecciones.length + " " + Arrays.toString(selecciones));
		
		return padres;
	}
	
	public double[] getProbSeleccion(Individuo[] individuos) {
		Arrays.sort(individuos);
		
		double[] pSel = new double[individuos.length];
		
		double total = 0;
		for (Individuo i : individuos) {
			total += i.getFitness();
		}
		
		System.out.println("Total: " + total);
		
		pSel[0] = (individuos[0].getFitness() / total);
				
		for (int i = 1; i < pSel.length; i++) {
			pSel[i] = (individuos[i].getFitness() / total) /*+ pSel[i - 1]*/;
			System.out.println(individuos[i].getFitness() + " / " + total + " = " + pSel[i]);
		}

		return pSel;
	}
}

