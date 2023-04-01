package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Reponse;

import java.util.List;

public interface ReponseService {

    Reponse createReponse(Reponse reponse);
    List<Reponse> getAllReponse();
    Reponse getReponseById(Long id);

    void delete(Reponse reponse);
}
