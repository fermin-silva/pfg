package es.uned.pfg.ae.params;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

/**
 * Validador de parametros numericos para asegurarse que estos sean mayores
 * que cero.
 *
 * @author Fermin Silva
 */
public class MayorQueCero implements IValueValidator<Integer> {

	@Override
	public void validate(String name, Integer i) throws ParameterException {
		if (i <= 0) {
			throw new ParameterException("El parametro " + name + 
										  " debe ser mayor que cero"); 
		}
	}
}

