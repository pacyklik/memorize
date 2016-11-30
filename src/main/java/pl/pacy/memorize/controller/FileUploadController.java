package pl.pacy.memorize.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pacy.memorize.entity.Lesson;
import pl.pacy.memorize.entity.Word;
import pl.pacy.memorize.repository.LessonRepository;
import pl.pacy.memorize.repository.WordRepository;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileUploadController {

	@Autowired
	private final LessonRepository lessonRepository;
	@Autowired
	private final WordRepository wordRepository;

	@Transactional
	@PostMapping("/api/fileUpload")
	public Map handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws IOException {

		String filename = file.getOriginalFilename();
		String lessonName = file.getOriginalFilename().substring(0, filename.indexOf("."));
		List<String> collect = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))
				.lines().collect(Collectors.toList());

		Lesson lesson = new Lesson();
		lesson.setName(lessonName);
		List<Word> words = new ArrayList<>();
		Word word = new Word();
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
				word.setLevelLearned((long) 0);
				word.setLesson(lesson);
				words.add(word);
				word = new Word();
				word.setSentence("");
				word.setSentenceTranslate("");
			}
		}

		lessonRepository.save(lesson);
		wordRepository.save(words);

		Map<String, String> response = new HashMap<>();
		response.put("lessonName",lessonName);
		response.put("result","OK");

		return response;
	}
}
