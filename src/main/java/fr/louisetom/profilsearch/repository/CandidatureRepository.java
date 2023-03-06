package fr.louisetom.profilsearch.repository;

import fr.louisetom.profilsearch.model.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
}
