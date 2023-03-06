package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.service.OffreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
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

    @GetMapping("/all")
    public List<Offre> getAllOffre() {

        List<Offre> offres = new ArrayList<Offre>();
        Offre offre = new Offre("Dev Java", new Date(), "Description", "CDI", "Paris", 3000);
        Offre offre2 = new Offre("Dev Python", new Date(), "Description", "CDI", "Paris", 3000);
        Offre offre3 = new Offre("Dev C++", new Date(), "Description", "CDI", "Paris", 3000);
        offres.add(offre);
        offres.add(offre2);
        offres.add(offre3);
        return offres;
        //return offreService.getAllOffre();
    }

    @GetMapping("/{id}")
    public Offre getOffreById(@PathVariable Long id) {
        return offreService.getOffreById(id);
    }

}
