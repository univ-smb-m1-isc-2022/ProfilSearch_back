package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Question_Offre;
import fr.louisetom.profilsearch.repository.Question_OffreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class Question_OffreServiceImpl implements Question_OffreService {

    private Question_OffreRepository question_offreRepository;

    @Override
    public Question_Offre createQuestion_Offre(Question_Offre question_offre) {
        return question_offreRepository.save(question_offre);
    }

    @Override
    public List<Question_Offre> getAllQuestion_Offre() {
        return question_offreRepository.findAll();
    }

    @Override
    public Question_Offre getQuestion_OffreById(Long id) {
        return question_offreRepository.findById(id).get();
    }
}
