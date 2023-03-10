package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.model.Question;
import fr.louisetom.profilsearch.repository.QuestionRepository;
import fr.louisetom.profilsearch.service.OffreService;
import fr.louisetom.profilsearch.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/profilsearch/question")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {

    private final QuestionService questionService;
    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @GetMapping("/all")
    public List<Question> getAllQuestion() {
        return questionService.getAllQuestion();
    }

    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }


}
