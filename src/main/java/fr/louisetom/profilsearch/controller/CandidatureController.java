
package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Candidature;
import fr.louisetom.profilsearch.model.Question;
import fr.louisetom.profilsearch.model.Reponse;
import fr.louisetom.profilsearch.repository.CandidatureRepository;
import fr.louisetom.profilsearch.repository.QuestionRepository;
import fr.louisetom.profilsearch.repository.ReponseRepository;
import fr.louisetom.profilsearch.service.CandidatureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public void createCandidature(@RequestBody Candidature candidature) {
        List<Reponse> reponses = candidature.getReponses();
        candidature.setReponses(null);
        Candidature savedCandidature = candidatureRepository.save(candidature);

        for (Reponse reponse : reponses) {
            System.out.println("reponse" + reponse.getReponse());
            Question question = questionRepository.findById(reponse.getQuestion().getId()).orElse(null);
            reponse.setQuestion(question);
            reponseRepository.save(reponse);
        }
        savedCandidature.setReponses(reponses);
    }

    @GetMapping("/getAll")
    public List<Candidature> getAllCandidature() {
        return candidatureService.getAllCandidature();
    }

    @GetMapping("/{id}")
    public Candidature getCandidatureById(@PathVariable Long id) {
        return candidatureService.getCandidatureById(id);
    }
}
