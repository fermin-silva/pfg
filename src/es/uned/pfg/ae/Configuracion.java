package es.uned.pfg.ae;

import es.uned.pfg.ae.utils.Aleatorio;

//TODO implementar utilizando parametros o GUI
public class Configuracion {

	public static final Aleatorio ALEATORIO_ESTATICO = 
											new Aleatorio(1425117445324L);
	
	public static final Aleatorio ALEATORIO_DEFAULT = new Aleatorio();
	
	public int getTamañoPoblacion() {
		return 2000;
	}
	
	public int getMaxIteraciones() {
		return 300;
	}
}
