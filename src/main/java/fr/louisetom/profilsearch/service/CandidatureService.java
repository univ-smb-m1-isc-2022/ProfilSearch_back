package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Candidature;

import java.util.List;

public interface CandidatureService {

    Candidature createCandidature(Candidature candidature);
    List<Candidature> getAllCandidature();
    Candidature getCandidatureById(Long id);
    List<Candidature> getCandidaturesByOffre(Long id);

    Candidature getCandidatureByToken(String token);


}
