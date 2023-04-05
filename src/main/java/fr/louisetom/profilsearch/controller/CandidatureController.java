
package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.mail.MailConfiguration;
import fr.louisetom.profilsearch.mail.MailService;
import fr.louisetom.profilsearch.model.Candidature;
import fr.louisetom.profilsearch.model.Question;
import fr.louisetom.profilsearch.model.Reponse;
import fr.louisetom.profilsearch.repository.CandidatureRepository;
import fr.louisetom.profilsearch.repository.QuestionRepository;
import fr.louisetom.profilsearch.repository.ReponseRepository;
import fr.louisetom.profilsearch.service.CandidatureService;
import fr.louisetom.profilsearch.service.QuestionService;
import fr.louisetom.profilsearch.service.ReponseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profilsearch/candidature")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://profilsearch.oups.net"})
public class CandidatureController {
    private final CandidatureService candidatureService;

    private final ReponseService reponseService;
    private final QuestionService questionService;

    @Autowired
    private JavaMailSender javaMailSender;
    private MailService mailService;

    @PostMapping("/create")
    public void createCandidature(@RequestBody Candidature candidature) throws MessagingException, IOException {
        List<Reponse> reponses = candidature.getReponses();
        candidature.setReponses(null);

        Candidature savedCandidature = candidatureService.createCandidature(candidature);

        for (Reponse reponse : reponses) {
            Optional<Question> questionOptional = questionService.getQuestionById(reponse.getQuestion().getId());
            if (questionOptional.isPresent()) {
                reponse.setQuestion(questionOptional.get());
                reponseService.createReponse(reponse);
            } else {
                reponse.setQuestion(null);
                reponseService.createReponse(reponse);
            }
        }
        savedCandidature.setReponses(reponses);

        mailService.sendMail(savedCandidature);
    }

    @GetMapping("/getAll")
    public List<Candidature> getAllCandidature() {
        return candidatureService.getAllCandidature();
    }

    @GetMapping("/{id}")
    public Candidature getCandidatureById(@PathVariable Long id) {
        return candidatureService.getCandidatureById(id);
    }

    @GetMapping("/offre/{id}")
    public List<Candidature> getCandidaturesByOffre(@PathVariable Long id) {
        return candidatureService.getCandidaturesByOffre(id);
    }

    @GetMapping("/token/{token}")
    public Candidature getCandidatureByToken(@PathVariable String token) {
        return candidatureService.getCandidatureByToken(token);
    }

    @GetMapping("/delete/{token}")
    public void deleteCandidatureByToken(@PathVariable String token) {
        Candidature candidature = candidatureService.getCandidatureByToken(token);
        for (Reponse reponse : candidature.getReponses()) {
            reponseService.delete(reponse);
        }
        candidatureService.deleteCandidature(candidature);
    }


}
