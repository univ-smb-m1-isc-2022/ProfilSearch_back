package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.service.OffreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profilsearch/offre")
@AllArgsConstructor
public class OffreController {
    private final OffreService offreService;

    @PostMapping("/create")
    public Offre createOffre(Offre offre) {
        return offreService.createOffre(offre);
    }

    @PostMapping("/getAll")
    public List<Offre> getAllOffre() {
        return offreService.getAllOffre();
    }

    @PostMapping("/getById")
    public Offre getOffreById(Long id) {
        return offreService.getOffreById(id);
    }



}
