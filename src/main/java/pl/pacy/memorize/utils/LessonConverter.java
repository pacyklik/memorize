package pl.pacy.memorize.utils;

import org.dozer.DozerConverter;
import pl.pacy.memorize.entity.Lesson;

public class LessonConverter extends DozerConverter<String, Lesson> {

	public LessonConverter() {
		super(String.class, Lesson.class);
	}

	@Override public Lesson convertTo(String source, Lesson destination) {
		return null;
	}

	@Override public String convertFrom(Lesson source, String destination) {
		return (source == null) ? null : source.getName();
	}
}
