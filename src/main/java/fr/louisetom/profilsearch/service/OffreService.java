package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Offre;

import java.util.List;

public interface OffreService {

    Offre createOffre(Offre offre);
    List<Offre> getAllOffre();
    Offre getOffreById(Long id);
}
