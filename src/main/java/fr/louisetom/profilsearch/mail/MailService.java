package fr.louisetom.profilsearch.mail;

import fr.louisetom.profilsearch.model.Candidature;
import fr.louisetom.profilsearch.model.Invitation;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private final Dotenv dotenv = Dotenv.load();


    public void sendMail(Candidature candidature) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String MAIL_USERNAME="profilsearch.help@gmail.com";
        String MAIL_PASSWORD="qmtgzqtyldlgzmah";

        helper.setFrom(new InternetAddress(MAIL_USERNAME, "ProfilSearch"));
        helper.setTo(candidature.getEmail());
        helper.setSubject("Supprimer vos données de candidature de ProfilSearch");

        String htmlMsg = "<h1>Supprimer vos données de candidature de ProfilSearch</h1>"
                + "<p>Vous avez répondu à une candidature sur ProfilSearch, vous pouvez supprimer vos données en cliquant sur le lien suivant : </p>"
                + "<a href='http://localhost:3000/delete/" + candidature.getToken() + "'>Supprimer mes données</a>"
                + "<p>Si vous n'avez pas répondu à cette candidature, vous pouvez ignorer cet email.</p>"
                + "<p>Cordialement,</p>"
                + "<p>L'équipe ProfilSearch</p>";

        helper.setText(htmlMsg, true);

        this.javaMailSender.send(message);

    }

    public void sendMailInvitation(Invitation invitation) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");

        String MAIL_USERNAME="profilsearch.help@gmail.com";
        String MAIL_PASSWORD="qmtgzqtyldlgzmah";

        helper.setFrom(new InternetAddress(MAIL_USERNAME, "ProfilSearch"));
        helper.setTo(invitation.getEmail());
        helper.setSubject("Invitation à devenir administrateur de ProfilSearch");

        String contentId = "logoImage";
        ByteArrayResource imageResource = new ByteArrayResource(Files.readAllBytes(Paths.get("src/main/resources/static/logo.png")));


        String htmlMsg = "<h1>Devenez administrateur de ProfilSearch</h1>" +
                "Vous avez été invité à devenir administrateur de ProfilSearch, cliquez sur le lien suivant pour vous inscrire : </p>"
                + "<a href='http://localhost:3000/signup'> S'inscrire </a>"
                + "<p>Si vous ne connaissez pas ProfilSearch, vous pouvez ignorer cet email.</p>"
                + "<p>Cordialement,</p>"
                + "<p>L'équipe ProfilSearch</p>"
                +"<img src='cid:" + contentId + "' style='width: 400px;'/>";

        helper.setText(htmlMsg, true);


        System.out.println(imageResource.getFilename());
        helper.addInline(contentId, imageResource, "image/png");


        javaMailSender.send(message);
    }

}
