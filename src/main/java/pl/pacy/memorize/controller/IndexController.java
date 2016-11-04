package pl.pacy.memorize.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Created by pacy on 2016-10-25.
 */
@Controller
public class IndexController {
	@GetMapping("/")
	public String index(Map<String, Object> model) {
		return "index";
	}
}
