package fr.louisetom.profilsearch;

import fr.louisetom.profilsearch.config.AppProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ProfilsearchApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(ProfilsearchApplication.class, args);
	}
}
