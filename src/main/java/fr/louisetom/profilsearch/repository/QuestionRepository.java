package fr.louisetom.profilsearch.repository;

import fr.louisetom.profilsearch.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
