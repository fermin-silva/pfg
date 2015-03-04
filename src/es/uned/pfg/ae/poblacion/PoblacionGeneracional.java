package es.uned.pfg.ae.poblacion;

import es.uned.pfg.ae.Individuo;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class PoblacionGeneracional extends Poblacion {

	public PoblacionGeneracional(Individuo[] individuos) {
		super(individuos);
	}
	
	@Override
	public void setSobrevivientes(Individuo[] padres, Individuo[] crias) {
		this.individuos = crias;
		
		setMejorIndividuo(getMejorIndividuo(crias));
	}
}

