package pl.pacy.memorize;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pacy.memorize.entity.Lesson;
import pl.pacy.memorize.entity.Word;
import pl.pacy.memorize.repository.LessonRepository;
import pl.pacy.memorize.repository.WordRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

	private void createLesson(String lessonName) throws Exception {
		String fileName = "/lessons/" + lessonName + ".txt";
		Lesson lesson = Lesson.builder().name(lessonName).build();
		List<Word> words = new ArrayList<>();
		Word word = new Word();
		word.setSentence("");
		word.setSentenceTranslate("");
		InputStream inputStream = MemorizeApplication.class.getResourceAsStream(fileName);
		List<String> collect = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")).lines().collect(Collectors.toList());
		Random r = new Random();

		for (String l : collect) {
			String[] sentence = l.split("\\t");
			if (l.contains(".") || l.contains("?")) {
				word.setSentence(sentence[1] + "\n" + word.getSentence());
				word.setSentenceTranslate(sentence[0] + "\n" + word.getSentenceTranslate());
			} else {
				word.setWord(sentence[1]);
				word.setTranslate(sentence[0]);
				word.setPrepared(true);
				word.setKnow(false);
				word.setLevelLearned((long) r.nextInt(10));
				word.setLesson(lesson);
				words.add(word);
				word = new Word();
				word.setSentence("");
				word.setSentenceTranslate("");
			}
		}

		lessonRepository.save(lesson);
		wordRepository.save(words);

		System.out.println(words);
	}

	@PostConstruct
	@Transactional
	public void start() {

		{
			Lesson l = Lesson.builder().name("0100").build();
			List<Word> words = new ArrayList<>();
			words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("próba").word("attempt").build());
			words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("troska").word("concern").build());
			words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("okazja").word("opportunity").build());
			words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("stanowisko").word("post").build());
			words.add(Word.builder().prepared(false).know(false).levelLearned(0l).lesson(l).translate("niedawny").word("recent").build());

			lessonRepository.save(l);
			wordRepository.save(words);
		}

		{
			try {
				createLesson("0100");
				createLesson("0200");
				createLesson("0300");
				createLesson("0400");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MemorizeApplication.class, args);
	}
}
