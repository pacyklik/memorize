package pl.pacy.memorize.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {

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
