package pl.pacy.memorize.service;

import com.querydsl.jpa.JPQLQuery;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pacy.memorize.dto.FilterWordDTO;
import pl.pacy.memorize.dto.LessonDTO;
import pl.pacy.memorize.dto.WordDTO;
import pl.pacy.memorize.entity.Lesson;
import pl.pacy.memorize.entity.QLesson;
import pl.pacy.memorize.entity.QWord;
import pl.pacy.memorize.entity.Word;
import pl.pacy.memorize.repository.LessonRepository;
import pl.pacy.memorize.repository.WordRepository;
import pl.pacy.memorize.utils.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private Mapper mapper;

	@Override public WordDTO getWord(Long id) {
		Word word = wordRepository.findOne(id);
		WordDTO wordDTO = mapper.map(word, WordDTO.class);
		return wordDTO;
	}

	@Override public WordDTO getWordWithSentences(Long id) {
		return null;
	}

	@Override public List<WordDTO> getWords(Long page, Map criteria) {
		QWord qWord = QWord.word1;
		QLesson qLesson = QLesson.lesson;
		FilterWordDTO filterWordDTO = mapper.map(criteria, FilterWordDTO.class);
		JPQLQuery<Word> query = QueryBuilder.<Word>builder(entityManager, qWord)
				.like(qWord.word, filterWordDTO.getWord())
				.like(qWord.translate, filterWordDTO.getTranslate())
				.eq(qWord.prepared, filterWordDTO.getPrepared())
				.eq(qWord.know, filterWordDTO.getKnow())
				.gt(qWord.levelLearned, filterWordDTO.getGtLevelLearned())
				.lt(qWord.levelLearned, filterWordDTO.getLtLevelLearned())
				.joinOn(qWord.lesson(), qLesson,
						QueryBuilder.predicateBuilder().like(qLesson.name, filterWordDTO.getLesson()).build())
				.build();
		List<WordDTO> collect = query.fetch().stream()
				.map(word -> mapper.map(word, WordDTO.class))
				.collect(Collectors.toList());
		return collect;
	}

	@Override public List<WordDTO> getWordsWithSentences(Long page, Map criteria) {
		return null;
	}

	@Override public List<LessonDTO> getLessons() {
		List<Lesson> lessons = lessonRepository.findAll();
		List<LessonDTO> lessonDTOs = lessons.stream().map(l -> mapper.map(l, LessonDTO.class)).collect(Collectors.toList());
		return lessonDTOs;
	}
}
