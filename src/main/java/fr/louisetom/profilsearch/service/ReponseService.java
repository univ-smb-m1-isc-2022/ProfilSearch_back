package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Reponse;

import java.util.List;
import java.util.Optional;

public interface ReponseService {

    Reponse createReponse(Reponse reponse);
    List<Reponse> getAllReponse();
    Optional<Reponse> getReponseById(Long id);

    void delete(Reponse reponse);
}
