package pl.pacy.memorize.service;

import pl.pacy.memorize.dto.LessonDTO;
import pl.pacy.memorize.dto.WordDTO;
import pl.pacy.memorize.dto.WordListDTO;

import java.util.List;
import java.util.Map;

public interface WordService {

	public WordDTO getWord(Long id);

	public WordDTO getWordWithSentences(Long id);

	public WordListDTO getWords(Integer page, Map criteria);

	public List<WordDTO> getWordsWithSentences(Integer page, Map criteria);

	public List<LessonDTO> getLessons();

}
