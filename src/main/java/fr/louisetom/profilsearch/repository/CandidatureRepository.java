package fr.louisetom.profilsearch.repository;

import fr.louisetom.profilsearch.model.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    List<Candidature> findAllByOffreId(Long id);
}
