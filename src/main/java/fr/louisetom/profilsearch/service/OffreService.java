package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Offre;

import java.util.List;
import java.util.Optional;

public interface OffreService {

    Offre createOffre(Offre offre);
    List<Offre> getAllOffre();
    Optional<Offre> getOffreById(Long id);
}
