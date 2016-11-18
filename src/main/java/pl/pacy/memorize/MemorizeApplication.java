package pl.pacy.memorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pacy.memorize.entity.Lesson;
import pl.pacy.memorize.entity.Word;
import pl.pacy.memorize.repository.LessonRepository;
import pl.pacy.memorize.repository.WordRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pacy on 2016-10-25.
 */
@SpringBootApplication(scanBasePackages = { "pl.pacy.memorize" })
public class MemorizeApplication {

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private WordRepository wordRepository;

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
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MemorizeApplication.class, args);
	}
}
