package fr.louisetom.profilsearch.mail;

import fr.louisetom.profilsearch.model.Candidature;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.experimental.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private final Dotenv dotenv = Dotenv.load();

    public void sendMail(Candidature candidature) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(new InternetAddress(dotenv.get("MAIL_USERNAME"), "ProfilSearch"));
        helper.setTo(candidature.getEmail());
        helper.setSubject("Supprimer vos données de candidature de ProfilSearch");

        String htmlMsg = "<h1>Supprimer vos données de candidature de ProfilSearch</h1>"
                + "<p>Vous avez répondu à une candidature sur ProfilSearch, vous pouvez supprimer vos données en cliquant sur le lien suivant : </p>"
                + "<a href='http://localhost:4200/delete/" + candidature.getToken() + "'>Supprimer mes données</a>"
                + "<p>Si vous n'avez pas répondu à cette candidature, vous pouvez ignorer cet email.</p>"
                + "<p>Cordialement,</p>"
                + "<p>L'équipe ProfilSearch</p>";

        helper.setText(htmlMsg, true);

        this.javaMailSender.send(message);

    }

}
