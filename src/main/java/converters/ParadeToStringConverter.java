
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Parade;

@Component
@Transactional
public class ParadeToStringConverter implements Converter<Parade, String> {

	@Override
	public String convert(final Parade a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
