package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Question_Offre;
import fr.louisetom.profilsearch.service.Question_OffreService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profilsearch/question_offre")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class Question_OffreController {
    private final Question_OffreService question_offreService;

    //@PostMapping("/create")
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)

    public void createQuestion_Offre(@RequestBody Question_Offre question_offre) {
        question_offreService.createQuestion_Offre(question_offre);
    }

    @GetMapping("/all")
    public void getAllQuestion_Offre() {
        question_offreService.getAllQuestion_Offre();
    }

    @GetMapping("/getById")
    public void getQuestion_OffreById(Long id) {
        question_offreService.getQuestion_OffreById(id);
    }

}
