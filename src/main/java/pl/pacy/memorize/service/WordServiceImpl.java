package pl.pacy.memorize.service;

import com.querydsl.core.types.dsl.BooleanExpression;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

	private List<WordDTO> getWordsAlt(Long page, Map criteria) {
		QWord qWord = QWord.word1;
		QLesson qLesson = QLesson.lesson;
		FilterWordDTO filterWordDTO = mapper.map(criteria, FilterWordDTO.class);
		JPQLQuery<Word> query = QueryBuilder.<Word>builder(entityManager, qWord)
				.like(qWord.word, filterWordDTO.getWord())
				.like(qWord.translate, filterWordDTO.getTranslate())
				.joinOn(qWord.lesson(),
						QueryBuilder.predicateBuilder().like(qLesson.name, filterWordDTO.getLesson()).build())
				.build();
		List<WordDTO> collect = query.fetch().stream().map(word -> mapper.map(word, WordDTO.class)).collect(Collectors.toList());
		return collect;
	}

	@Override public List<WordDTO> getWords(Long page, Map criteria) {
		if (true) {
			return getWordsAlt(page, criteria);
		}

		QWord qWord = QWord.word1;
		QLesson qLesson = QLesson.lesson;
		List<BooleanExpression> expressions = new ArrayList<>();

		if (criteria.get("word") != null && !((String) criteria.get("word")).isEmpty()) {
			expressions.add(qWord.word.like("%" + criteria.get("word") + "%"));
		}
		if (criteria.get("translate") != null && !((String) criteria.get("translate")).isEmpty()) {
			expressions.add(qWord.translate.like("%" + criteria.get("translate") + "%"));
		}
		if (criteria.get("lesson") != null && !((String) criteria.get("lesson")).isEmpty()) {

		}

//		Lesson.builder().id().build();

		BooleanExpression all = null;
		//		all = qWord.lesson().eqAny(qLesson.name.like("%" + criteria.get("lesson") + "%"));

		for (BooleanExpression p : expressions) {
			all = (all == null) ? p : all.and(p);
		}
		Iterable<Word> words = null;
		if (all != null) {
			words = wordRepository.findAll(all);
		} else {
			words = wordRepository.findAll();
		}

		List<WordDTO> wordDTOs = StreamSupport.stream(words.spliterator(), false).map(word -> mapper.map(word, WordDTO.class)).collect(Collectors.toList());

		return wordDTOs;
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
