package pl.pacy.memorize.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pacy on 2016-10-26.
 */
@RestController
public class BookControler {
	@GetMapping("/api/books/categories")
	public List categories() {
		List categories = new ArrayList<>();
		categories.add("HORROR");
		categories.add("FANTASY");
		categories.add("DRAMAT");
		categories.add("COMEDY");
		categories.add("THRILLER");
		categories.add("SCIENCE");
		categories.add("CRIMINAL");

		return categories;
	}

	@GetMapping("/api/books/find/{page}/{limit}/{sortColumn}/{dir}")
	public Map getBooks(@PathVariable Long page, @PathVariable Long limit, @PathVariable String sortColumn, @PathVariable String dir) {
		List books = new ArrayList<>();

		Map<String, Object> author1 = new HashMap<>();
		author1.put("authorId", "1");
		author1.put("authorFirstName", "Stephen");
		author1.put("authorLastName", "King");

		Map<String, Object> book1 = new HashMap<>();
		book1.put("bookId", "1");
		book1.put("category", "FANTASY");
		book1.put("title", "Apokryfy. Doskonala proznia");
		book1.put("isbn", "83-7006-710-721-1");
		book1.put("releaseDate", "2008-01-01");
		book1.put("publisher", "Wydawnictwo Literackie");
		book1.put("pages", "224");
		book1.put("description", "Super ksiazka");
		book1.put("author", author1);

		books.add(book1);

		// mapping to return data
		Map<String, Object> returnData = new HashMap<>();
		returnData.put("totalCount","9");
		returnData.put("books", books);

		return returnData;
	}
}
