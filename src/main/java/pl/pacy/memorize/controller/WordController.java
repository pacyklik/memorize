package pl.pacy.memorize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pacy.memorize.dto.WordDTO;
import pl.pacy.memorize.service.WordService;

import java.util.List;
import java.util.Map;

@RestController
public class WordController {

	@Autowired
	private WordService wordService;

	@GetMapping("/api/words/find/{page}")
	public List findWords(@PathVariable Long page, @RequestParam Map<String, String> params) {
		System.out.println(page + " => " + params);
		List<WordDTO> words = wordService.getWords(page, params);
		return words;
	}
}
