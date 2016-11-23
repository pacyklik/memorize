package pl.pacy.memorize.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterWordDTO {

	private Long id;
	private String word;
	private String translate;
	private List<String> sentence;
	private List<String> sentenceTranslate;
	private Boolean know;
	private Boolean prepared;
	private Long levelLearned;
	private String lesson;
}
