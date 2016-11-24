package pl.pacy.memorize.utils;

import org.dozer.CustomConverter;
import pl.pacy.memorize.dto.FilterWordDTO;

import static java.util.Optional.ofNullable;

public class ThanLevelLearnedConverter implements CustomConverter {

	@Override public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
		return (sourceFieldValue == null) ? null : FilterWordDTO.ThanLevelLearned.valueOf((String) sourceFieldValue);
	}

	// java8 way but what?
	public Object convert8(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
		return ofNullable((String) sourceFieldValue)
				.map(s -> FilterWordDTO.ThanLevelLearned.valueOf(s))
				.orElse(null);
	}
}
