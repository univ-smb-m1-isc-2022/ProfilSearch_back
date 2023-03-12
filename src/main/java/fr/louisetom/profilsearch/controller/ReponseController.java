package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Reponse;
import fr.louisetom.profilsearch.service.ReponseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profilsearch/reponse")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReponseController {

    private final ReponseService reponseService;

    @PostMapping("/create")
    public void createReponse(@RequestBody Reponse reponse) {
        reponseService.createReponse(reponse);
    }

    @GetMapping("/getAll")
    public void getAllReponse() {
        reponseService.getAllReponse();
    }

    @GetMapping("/{id}")
    public void getReponseById(@PathVariable Long id) {
        reponseService.getReponseById(id);
    }
}
