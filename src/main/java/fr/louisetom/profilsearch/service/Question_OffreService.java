package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Question_Offre;

import java.util.List;

public interface Question_OffreService {

    Question_Offre createQuestion_Offre(Question_Offre question_offre);
    List<Question_Offre> getAllQuestion_Offre();
    Question_Offre getQuestion_OffreById(Long id);

}
