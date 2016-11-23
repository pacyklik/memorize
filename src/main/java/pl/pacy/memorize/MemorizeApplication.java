package pl.pacy.memorize;

//import com.mysema.query.types.expr.BooleanExpression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pacy.memorize.dto.LessonDTO;
import pl.pacy.memorize.dto.WordDTO;
import pl.pacy.memorize.entity.Lesson;
import pl.pacy.memorize.entity.QLesson;
import pl.pacy.memorize.entity.QWord;
import pl.pacy.memorize.entity.Word;
import pl.pacy.memorize.repository.LessonRepository;
import pl.pacy.memorize.repository.WordRepository;

import pl.pacy.memorize.utils.QueryBuilder;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pacy on 2016-10-25.
 */
@SpringBootApplication(scanBasePackages = { "pl.pacy.memorize" })
public class MemorizeApplication {

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private Mapper mapper;

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	@Transactional
	public void start() {

		Lesson l = Lesson.builder().name("0100").build();
		List<Word> words = new ArrayList<>();
		words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("próba").word("attempt").build());
		words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("troska").word("concern").build());
		words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("okazja").word("opportunity").build());
		words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("stanowisko").word("post").build());
		words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("niedawny").word("recent").build());

		lessonRepository.save(l);
		wordRepository.save(words);

		{
			LessonDTO lessonDTO = mapper.map(l, LessonDTO.class);
			System.out.println(lessonDTO);

			WordDTO wordDTO = mapper.map(wordRepository.findOne(2L), WordDTO.class);
			System.out.println(wordDTO);

			QWord qWord = QWord.word1;
			BooleanExpression concern = qWord.word.like("%con%");
			//		BooleanExpression troska = qWord.translate.eq("troska");
			//		Predicate criteria = (Predicate) concern.and(troska);
			Iterable<Word> all = wordRepository.findAll(concern);

			System.out.println(all);
		}
		////////////////////////////////////////////////////////////
		{
			QWord qWord = QWord.word1;
			QLesson qLesson = QLesson.lesson;
			JPQLQuery<Word> query = new JPAQuery(entityManager);
			query = query.from(qWord);
			query.innerJoin(qWord.lesson(), qLesson);
			query.on(qLesson.name.like("%2%"));
			List<Word> fetch = query.fetch();
			//			entityManager.createQuery(query);
//			List fetch = query.list(qWord);
//			System.out.println(fetch);
		}

		{
			QWord qWord = QWord.word1;
			QLesson qLesson = QLesson.lesson;
			JPQLQuery<Word> query = QueryBuilder.<Word>builder(entityManager, qWord)
					.like(qWord.word, "")
					.like(qWord.translate, "")
					.joinOn(qWord.lesson(),
							QueryBuilder.predicateBuilder().like(qLesson.name, "").build())
									.build();
		}

	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MemorizeApplication.class, args);
	}
}
