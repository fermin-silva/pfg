package es.uned.pfg.ae.poblacion;

import es.uned.pfg.ae.Individuo;

/**
 * Modelo poblacional llamado generacional, porque reemplaza la poblacion
 * completa en cada generacion por los nuevos individuos (los hijos).
 * A su vez, puede ser elitista o no. Si lo es, esta conservara en todo momento
 * al mejor individuo obtenido, sin importar su generacion.
 */
public class PoblacionGeneracional extends Poblacion {

	private boolean elitista;

	public PoblacionGeneracional(Individuo[] individuos, boolean elitista) {
		super(individuos);
		
		this.elitista = elitista;
	}
	
	@Override
	public void setSobrevivientes(Individuo[] crias) {
		this.individuos = crias;
		
		if (elitista) {
			Individuo nuevoMejor = getMejorIndividuo(crias);

			if (getMejorIndividuo().getFitness() > nuevoMejor.getFitness()) {
				//ninguna de las crias supera al mejor individuo anterior, por lo cual
				//se conserva en la poblacion
				int indice = encontrarPeor(crias);
				this.individuos[indice] = getMejorIndividuo();
				
				//no hace falta llamar a setMejorIndividuo ya que no se ha cambiado
			}
			else {
				//el nuevo mejor supera la generacion anterior, por lo cual es elegido
				setMejorIndividuo(nuevoMejor);
			}
		}
		else {
			setMejorIndividuo(getMejorIndividuo(crias));
		}
	}
}

