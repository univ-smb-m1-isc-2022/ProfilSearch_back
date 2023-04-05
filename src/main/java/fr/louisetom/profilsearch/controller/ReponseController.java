package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Reponse;
import fr.louisetom.profilsearch.service.ReponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profilsearch/reponse")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://profilsearch.oups.net"})
public class ReponseController {

    private final ReponseService reponseService;

    @PostMapping("/create")
    public void createReponse(@RequestBody Reponse reponse) {
        reponseService.createReponse(reponse);
    }

    @GetMapping("/getAll")
    public List<Reponse> getAllReponse() {
        return reponseService.getAllReponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reponse> getReponseById(@PathVariable Long id) {
        Optional<Reponse> reponse = reponseService.getReponseById(id);
        return reponse.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
