package pl.pacy.memorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by pacy on 2016-10-25.
 */
@SpringBootApplication(scanBasePackages = { "pl.pacy.memorize" })
public class MemorizeApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MemorizeApplication.class, args);
	}
}
