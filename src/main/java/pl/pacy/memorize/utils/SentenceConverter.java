package pl.pacy.memorize.utils;

import org.dozer.CustomConverter;
import pl.pacy.memorize.dto.FilterWordDTO;

import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

public class SentenceConverter implements CustomConverter {

	@Override public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
		if (sourceFieldValue != null) {
			List<String> list = Arrays.asList(((String) sourceFieldValue).split("\n"));
			return list;
		}
		return null;
	}
}
