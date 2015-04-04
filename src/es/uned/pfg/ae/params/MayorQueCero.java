package es.uned.pfg.ae.params;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
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

