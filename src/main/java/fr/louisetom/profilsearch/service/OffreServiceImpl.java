package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.repository.OffreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OffreServiceImpl implements OffreService {
    private final OffreRepository offreRepository;
    @Override
    public Offre createOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    @Override
    public List<Offre> getAllOffre() {
        return offreRepository.findAll();
    }

    @Override
    public Offre getOffreById(Long id) {
        return offreRepository.findById(id).orElse(null);
    }

}
