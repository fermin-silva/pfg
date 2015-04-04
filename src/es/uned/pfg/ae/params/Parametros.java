package es.uned.pfg.ae.params;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;


/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Parametros {

	@Parameter(names = { "-tamaño", "-t" }, 
			   description = "El tamaño de la poblacion")
	public Integer tamañoPoblacion;
	
	//TODO si la cantidad es -1, iterar hasta que converja
	
	@Parameter(names = { "-iteraciones", "-i" }, 
			   validateValueWith = MayorQueCero.class,
			   description = "La cantidad de iteraciones")
	public Integer generaciones;
	
	@Parameter(names = { "-dimension", "-d" },
			   validateValueWith = MayorQueCero.class,
			   description = "La dimension de la funcion (si es que la " +
			   				 "funcion asi lo requiere)")
	public Integer dimension = 2;
	
	@Parameter(names = { "-funcion", "-f" }, 
			   description = "El nombre de la funcion a optimizar (Ackley, " +
			   				 "Griewank, Rastrigin, Schaffer2, Schubert, " +
			   				 "Schewefel)")
	public String funcion;
	
	@Parameter(names = { "-properties", "-p" }, 
			   description = "Archivo con propiedades, alternativo al uso " +
			   				 "de parametros. Los parametros tienen mas " +
			   				 "prioridad que las propiedades")
	public String properties;
	
	@Parameter(names = { "-tamañoTorneo" },
			   validateValueWith = MayorQueCero.class,
			   description = "Tamaño del torneo (solamente utilizada para " +
			   				 "la Seleccion por Torneo)")
	public Integer tamañoTorneo;
	
	@Parameter(names = { "-elitismo" }, 
			   description = "Si esta presente se activa el elitismo, de lo " +
			   				 "contrario se asume que no se utiliza")
	public Boolean elitismo;
	
	@Parameter(names = { "-alpha" }, validateValueWith = Validador01.class, 
			   description = "Para las recombinaciones que utilizan un valor " +
			   				 "alpha para el promedio ponderado")
	public Double alpha;
	
	@Parameter(names = { "-puntosCombinacion", "-k" },
			   validateValueWith = MayorQueCero.class,
			   description = "Cantidad de puntos de cruce (solo aplicable a " +
			   				 "la RecombinacionK)")
	public Integer puntosCombinacion;
	
	@Parameter(names = { "-desviacion"  }, 
			   description = "Para la mutacion normal, el valor de la " +
			   				 "desviacion tipica")
	public Double desviacionMutacion;
	
	@Parameter(names = { "-probRecombinacion" }, 
			   validateValueWith = Validador01.class,
			   description = "Probabilidad de que se recombinen los padres. " +
			   				 "Si no se recombinan, los hijos seran copias " +
			   				 "exactas de los padres")
	public Double probRecombinacion;
	
	@Parameter(names = { "-h" }, description = "Imprime esta ayuda",  
			   help = true)
	public Boolean ayuda;
	
	
	public static Parametros crear(String[] args) {
		Parametros parametros = new Parametros();
		JCommander jc = new JCommander(parametros);
		
		jc.parse(args);
		
		if (Boolean.TRUE.equals(parametros.ayuda)) {
			jc.usage();
			System.exit(0);
		}
		
		return parametros;
	}
}

