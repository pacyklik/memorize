package pl.pacy.memorize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pacy.memorize.dto.WordDTO;
import pl.pacy.memorize.dto.WordListDTO;
import pl.pacy.memorize.service.WordService;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class WordController {

	@Autowired
	private WordService wordService;

	private static final Logger log = java.util.logging.Logger.getLogger(WordController.class.getName());

	@GetMapping("/api/words/find/{page}")
	public WordListDTO findWords(@PathVariable Integer page, @RequestParam Map<String, String> params) {
		log.info(page + " => " + params);
		WordListDTO wordsListDTO = wordService.getWords(page, params);
		return wordsListDTO;
	}

	@GetMapping("/api/words/{id}")
	public WordDTO getWord(@PathVariable Long id) {
		WordDTO word = wordService.getWord(id);
		return word;
	}

	@PostMapping("/api/words/{id}")
	public WordDTO updateWord(@RequestBody WordDTO wordDTO) {
		wordDTO = wordService.update(wordDTO);
		return wordDTO;
	}

	@GetMapping("/api/words/to_check")
	public WordListDTO findWordsToCheck() {
		WordListDTO wordsListDTO = wordService.getWordsToCheck();
		return wordsListDTO;
	}
}
