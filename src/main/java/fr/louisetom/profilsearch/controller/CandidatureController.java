
package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Candidature;
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

    @PostMapping("/create")
    public void createCandidature(@RequestBody Candidature candidature) {
        candidatureService.createCandidature(candidature);
    }

    @GetMapping("/getAll")
    public List<Candidature> getAllCandidature() {
        return candidatureService.getAllCandidature();
    }

    @GetMapping("/getById")
    public Candidature getCandidatureById(Long id) {
        return candidatureService.getCandidatureById(id);
    }
}
