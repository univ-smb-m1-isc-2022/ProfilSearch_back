package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Candidature;
import fr.louisetom.profilsearch.repository.CandidatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidatureServiceImpl implements CandidatureService {
    public final CandidatureRepository candidatureRepository;

    @Override
    public Candidature createCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public List<Candidature> getAllCandidature() {
        return candidatureRepository.findAll();
    }

    @Override
    public Candidature getCandidatureById(Long id) {
        return candidatureRepository.findById(id).orElse(null);
    }

}
