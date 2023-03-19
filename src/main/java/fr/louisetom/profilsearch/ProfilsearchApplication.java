package fr.louisetom.profilsearch;

import fr.louisetom.profilsearch.config.AppProperties;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ProfilsearchApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String GOOGLE_CLIENT_ID = dotenv.get("GOOGLE_CLIENT_ID");
		String GOOGLE_CLIENT_SECRET = dotenv.get("GOOGLE_CLIENT_SECRET");

		org.springframework.boot.SpringApplication.run(ProfilsearchApplication.class, args);
	}
}
