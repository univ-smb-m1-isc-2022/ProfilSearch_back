
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
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/profilsearch/candidature")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CandidatureController {
    private final CandidatureService candidatureService;
    private final CandidatureRepository candidatureRepository;
    private final QuestionRepository questionRepository;
    private final ReponseRepository reponseRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    private MailService mailService;

    @PostMapping("/create")
    public void createCandidature(@RequestBody Candidature candidature) throws MessagingException, UnsupportedEncodingException {
        List<Reponse> reponses = candidature.getReponses();
        candidature.setReponses(null);

        Candidature savedCandidature = candidatureRepository.save(candidature);

        for (Reponse reponse : reponses) {
            Question question = questionRepository.findById(reponse.getQuestion().getId()).orElse(null);
            reponse.setQuestion(question);
            reponseRepository.save(reponse);
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
        return candidatureRepository.findByToken(token);
    }

    @GetMapping("/delete/{token}")
    public void deleteCandidatureByToken(@PathVariable String token) {
        Candidature candidature = candidatureRepository.findByToken(token);
        for (Reponse reponse : candidature.getReponses()) {
            reponseRepository.delete(reponse);
        }
        candidatureRepository.delete(candidature);
    }


}
