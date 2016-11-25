package pl.pacy.memorize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
