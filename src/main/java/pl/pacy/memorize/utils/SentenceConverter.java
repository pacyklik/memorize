package pl.pacy.memorize.utils;

import org.dozer.DozerConverter;

import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

public class SentenceConverter extends DozerConverter<String, List> {

	public SentenceConverter() {
		super(String.class, List.class);
	}

	@Override public List convertTo(String source, List destination) {
		// java 8
		return ofNullable(source).map(s -> Arrays.asList((s).split("\n"))).orElse(null);

		// java 7 way
		//		if (source != null) {
		//			List<String> list = Arrays.asList(((String) source).split("\n"));
		//			return list;
		//		}
		//		return null;
	}

	@Override public String convertFrom(List source, String destination) {
		// java 8
		return ofNullable((List<String>) source).map(s -> {
			String sentenceAll = null;
			for (String sentence : s) {
				sentenceAll = (sentenceAll == null) ? sentence : sentenceAll + "\n" + sentence;
			}
			return sentenceAll;
		}).orElse(null);

		// java 7
		//		if (source != null) {
		//			List<String> list = (List<String>) source;
		//			String sentenceAll = "";
		//			for (String sentence : list) {
		//				sentenceAll += sentence + "\n";
		//			}
		//			return sentenceAll;
		//		}
		//		return null;
	}
}
