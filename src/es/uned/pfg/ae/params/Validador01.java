package es.uned.pfg.ae.params;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

/**
 * Valida que un valor esté entre 0 y 1
 * 
 * @author Fermin Silva
 */
public class Validador01 implements IValueValidator<Double> {

	@Override
	public void validate(String name, Double d) throws ParameterException {
		if (d < 0 || d > 1) {
			throw new ParameterException("El parametro " + name + 
										  " debe estar entre [0, 1]");
		}
	}
}

