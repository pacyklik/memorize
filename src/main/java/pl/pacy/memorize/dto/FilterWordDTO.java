package pl.pacy.memorize.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterWordDTO {

	public enum ThanLevelLearned {
		greater,
		less
	}

	private Long id;
	private String word;
	private String translate;
	private List<String> sentence;
	private List<String> sentenceTranslate;
	private Boolean know;
	private Boolean prepared;
	private Long levelLearned;
	private ThanLevelLearned thanLevelLearned;
	private String lesson;

	public Long getGtLevelLearned() {
		return (levelLearned != null && thanLevelLearned == ThanLevelLearned.greater) ? levelLearned : null;
	}

	public Long getLtLevelLearned() {
		return (levelLearned != null && thanLevelLearned == ThanLevelLearned.less) ? levelLearned : null;
	}
}
