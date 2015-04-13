package es.uned.pfg.ae.params;

import static es.uned.pfg.ae.Configuracion.*;
import static java.lang.System.out;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import es.uned.pfg.ae.funcion.FuncionFactory;
import es.uned.pfg.ae.mutacion.MutacionFactory;
import es.uned.pfg.ae.recombinacion.RecombinacionFactory;
import es.uned.pfg.ae.seleccion.SeleccionFactory;


/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Parametros {

	@Parameter(names = { "-tamaño", "-t" },
			   validateValueWith = MayorQueCero.class,
			   description = "El tamaño de la poblacion")
	public Integer tamañoPoblacion;
	
	//TODO si la cantidad es -1, iterar hasta que converja
	
	@Parameter(names = { "-generaciones", "-g" }, 
			   validateValueWith = MayorQueCero.class,
			   description = "La cantidad de iteraciones. En archivo de " +
			   				 "properties es '" + PROP_GENERACIONES + "'")
	public Integer generaciones;
	
	@Parameter(names = { "-dimension", "-d" },
			   validateValueWith = MayorQueCero.class,
			   description = "La dimension de la funcion (si es que la " +
			   				 "funcion asi lo requiere). En archivo de " +
			   				 "properties es '" + PROP_DIMENSION_FUNC + "'")
	public Integer dimension = 2;
	
	@Parameter(names = { "-funcion", "-f" }, 
			   description = "El nombre de la funcion a optimizar. En " +
			   				 "archivo de properties es '" + PROP_FUNCION + "'")
	public String funcion;
	
	@Parameter(names = { "-properties", "-p" }, 
			   description = "Archivo con propiedades, alternativo al uso " +
			   				 "de parametros. Los parametros tienen mas " +
			   				 "prioridad que las propiedades")
	public String properties;
	
	@Parameter(names = { "-tamañoTorneo" },
			   validateValueWith = MayorQueCero.class,
			   description = "Tamaño del torneo (solamente utilizada para " +
			   				 "la Seleccion por Torneo). En archivo de " +
			   				 "properties es '" + PROP_TAMAÑO_TORNEO + "'")
	public Integer tamañoTorneo;
	
	@Parameter(names = { "-seleccion", "-s" }, 
			   description = "El nombre de la seleccion a utilizar. " +
			   				 "De no utilizar ninguna se toma por defecto la " +
			   				 "SeleccionNoOp (que no realiza nada). En " +
			   				 "archivo de properties es '" + PROP_SELECCION + "'")
	public String seleccion;
	
	@Parameter(names = { "-recombinacion", "-r" }, 
			   description = "El nombre de la recombinacion a utilizar. " +
			   				 "De no utilizar ninguna se toma por defecto la " +
			   				 "RecombinacionNoOp (que no realiza nada). En " +
			   				 "archivo de properties es '" + PROP_RECOMBINACION + "'")
	public String recombinacion;
	
	@Parameter(names = { "-mutacion", "-m" }, 
			   description = "El nombre de la mutacion a utilizar. " +
			   				 "De no utilizar ninguna se toma por defecto la " +
			   				 "MutacionNoOp (que no realiza nada). En archivo " +
			   				 "de properties es '" + PROP_MUTACION + "'")
	public String mutacion;
	
	@Parameter(names = { "-elitismo" }, 
			   description = "Si esta presente se activa el elitismo, de lo " +
			   				 "contrario se asume que no se utiliza. En " +
			   				 "archivo de properties es '" + PROP_ELITISMO + "'")
	public Boolean elitismo;
	
	@Parameter(names = { "-alpha" }, validateValueWith = Validador01.class, 
			   description = "Para las recombinaciones que utilizan un valor " +
			   				 "alpha para el promedio ponderado. En archivo " +
			   				 "de properties es '" + PROP_ALPHA + "'")
	public Double alpha;
	
	@Parameter(names = { "-beta" }, validateValueWith = Validador01.class, 
			   description = "Para las recombinaciones que utilizan un valor " +
			   				 "beta para el promedio ponderado de la segunda cria. " +
			   				 "En archivo de properties es '" + PROP_BETA + "'")
	public Double beta;
	
	@Parameter(names = { "-puntosCombinacion", "-k" },
			   validateValueWith = MayorQueCero.class,
			   description = "Cantidad de puntos de cruce (solo aplicable a " +
			   				 "la RecombinacionK). En archivo de properties " +
			   				 "es '" + PROP_PUNTOS_COMBINACION + "'")
	public Integer puntosCombinacion;
	
	@Parameter(names = { "-desviacion"  }, 
			   description = "Para la mutacion normal, el valor de la " +
			   				 "desviacion tipica. En archivo de properties " +
			   				 "es '" + PROP_DESVIACION_MUT + "'")
	public Double desviacionMutacion;
	
	@Parameter(names = { "-probRecombinacion" }, 
			   validateValueWith = Validador01.class,
			   description = "Probabilidad de que se recombinen los padres. " +
			   				 "Si no se recombinan, los hijos seran copias " +
			   				 "exactas de los padres. De no utilizarse se " +
			   				 "asume una probabilidad del 100%. En archivo de " +
			   				 "properties es '" + PROP_PROB_RECOMB + "'")
	public Double probRecombinacion;
	
	@Parameter(names = { "-probMutacion" }, 
			   validateValueWith = Validador01.class,
			   description = "Probabilidad de que se mute cada alelo. Se " +
			   				 "utiliza para la mutacion uniforme, la normal " +
			   				 "utiliza solo la desviacion. En archivo de " +
			   				 "properties es '" + PROP_PROB_MUT + "'")
	public Double probMutacion;
	
	@Parameter(names = { "-h" }, description = "Imprime esta ayuda",  
			   help = true)
	public Boolean ayuda;
	
	
	public static Parametros crear(String[] args) {
		Parametros parametros = new Parametros();
		JCommander jc = new JCommander(parametros);
		
		jc.parse(args);
		
		if (Boolean.TRUE.equals(parametros.ayuda)) {
			jc.usage();
			
			out.println("Las funciones disponibles para elegir son:\n\t" +
					   FuncionFactory.getNombres());
			
			out.println("Las mutaciones disponibles para elegir son:\n\t" +
			   		  	MutacionFactory.getNombres());
			
			out.println("Las selecciones disponibles para elegir son:\n\t" +
						SeleccionFactory.getNombres());
			
			out.println("Las recombinaciones disponibles para elegir son:\n\t" +
	   		   			RecombinacionFactory.getNombres());
			
			System.exit(0);
		}
		
		return parametros;
	}
}

