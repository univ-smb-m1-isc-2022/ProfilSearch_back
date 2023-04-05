package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Reponse;
import fr.louisetom.profilsearch.repository.ReponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReponseServiceImpl implements ReponseService {

    private final ReponseRepository reponseRepository;


    @Override
    public Reponse createReponse(Reponse reponse) {
        return reponseRepository.save(reponse);
    }

    @Override
    public List<Reponse> getAllReponse() {
        return reponseRepository.findAll();
    }

    @Override
    public Optional<Reponse> getReponseById(Long id) {
        return reponseRepository.findById(id);
    }


    @Override
    public void delete(Reponse reponse) {
        reponseRepository.delete(reponse);
    }
}
