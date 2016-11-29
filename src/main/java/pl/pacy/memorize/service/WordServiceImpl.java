package pl.pacy.memorize.service;

import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pacy.memorize.dto.FilterWordDTO;
import pl.pacy.memorize.dto.LessonDTO;
import pl.pacy.memorize.dto.WordDTO;
import pl.pacy.memorize.dto.WordListDTO;
import pl.pacy.memorize.entity.Lesson;
import pl.pacy.memorize.entity.QLesson;
import pl.pacy.memorize.entity.QWord;
import pl.pacy.memorize.entity.Word;
import pl.pacy.memorize.repository.LessonRepository;
import pl.pacy.memorize.repository.WordRepository;
import pl.pacy.memorize.utils.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WordServiceImpl implements WordService {

	@PersistenceContext
	private final EntityManager entityManager;

	@Autowired
	private final WordRepository wordRepository;

	@Autowired
	private final LessonRepository lessonRepository;

	@Autowired
	private final Mapper mapper;

	private static final Logger log = java.util.logging.Logger.getLogger(WordServiceImpl.class.getName());

	@Override public WordDTO getWord(Long id) {
		Word word = wordRepository.findOne(id);
		WordDTO wordDTO = mapper.map(word, WordDTO.class);
		return wordDTO;
	}

	@Transactional
	@Override public WordDTO update(WordDTO wordDTO) {
		Word word = wordRepository.findOne(wordDTO.getId());
		log.info("word> " + entityManager.contains(word));
		mapper.map(wordDTO, word);
		log.info("word> " + entityManager.contains(word));

		// java 8 way
		boolean prepared = !ofNullable(word).map(Word::getSentence).orElse("").isEmpty()
				&& !of(word).map(Word::getSentenceTranslate).orElse("").isEmpty();

		// java 7 way
		//		boolean prepared = false;
		//		if (word != null && word.getSentence() != null && !word.getSentence().isEmpty()) {
		//			if (word.getSentenceTranslate() != null && !word.getSentenceTranslate().isEmpty()) {
		//				prepared = true;
		//			}
		//		}

		word.setPrepared(prepared);
		return wordDTO;
	}

	@Override public WordDTO getWordWithSentences(Long id) {
		return null;
	}

	@Override public WordListDTO getWords(Integer page, Map criteria) {
		QWord qWord = QWord.word1;
		QLesson qLesson = QLesson.lesson;
		// pagination
		Pageable pageable = new PageRequest(page, 10);
		// filter
		FilterWordDTO filterWordDTO = mapper.map(criteria, FilterWordDTO.class);
		// build query
		JPQLQuery<Word> query = QueryBuilder.<Word>builder(entityManager, qWord, Word.class)
				.select(qWord.id, qWord.word, qWord.translate, qWord.lesson(), qWord.prepared, qWord.levelLearned)
				.like(qWord.word, filterWordDTO.getWord())
				.like(qWord.translate, filterWordDTO.getTranslate())
				.eq(qWord.prepared, filterWordDTO.getPrepared())
				.eq(qWord.know, filterWordDTO.getKnow())
				.gt(qWord.levelLearned, filterWordDTO.getGtLevelLearned())
				.lt(qWord.levelLearned, filterWordDTO.getLtLevelLearned())
				.joinOn(qWord.lesson(), qLesson,
						QueryBuilder.predicateBuilder().like(qLesson.name, filterWordDTO.getLesson()).build())
				.pagination(pageable)
				.build();
		// prepare return dto
		WordListDTO wordListDTO = WordListDTO.builder()
				.count(query.fetchCount())
				.words(query.fetch().stream()
						.map(word -> mapper.map(word, WordDTO.class))
						.collect(Collectors.toList()))
				.build();

		return wordListDTO;
	}

	@Override public List<WordDTO> getWordsWithSentences(Integer page, Map criteria) {
		return null;
	}

	@Override public List<LessonDTO> getLessons() {
		List<Lesson> lessons = lessonRepository.findAll();
		return lessons.stream().map(l -> mapper.map(l, LessonDTO.class)).collect(Collectors.toList());
	}

	@Override public WordListDTO getWordsToCheck() {
		return null;
	}
}
