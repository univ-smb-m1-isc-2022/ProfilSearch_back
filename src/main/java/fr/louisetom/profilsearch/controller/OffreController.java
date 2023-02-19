package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.service.OffreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profilsearch/offre")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OffreController {
    private final OffreService offreService;

    @PostMapping("/create")
    public Offre createOffre(Offre offre) {
        return offreService.createOffre(offre);
    }

    @GetMapping("/getAll")
    public List<Offre> getAllOffre() {
        return offreService.getAllOffre();
    }

    @GetMapping("/getById")
    public Offre getOffreById(Long id) {
        return offreService.getOffreById(id);
    }



}
