package fr.louisetom.profilsearch.mail;

import fr.louisetom.profilsearch.model.Candidature;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class MailConfiguration {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public JavaMailSender getJavaMailSender() {


        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        String MAIL_USERNAME="profilsearch.help@gmail.com";
        String MAIL_PASSWORD="qmtgzqtyldlgzmah";


        mailSender.setUsername(MAIL_USERNAME);
        mailSender.setPassword(MAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

}
