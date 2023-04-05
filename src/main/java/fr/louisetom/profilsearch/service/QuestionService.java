package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(Question question);
    List<Question> getAllQuestion();
    Optional<Question> getQuestionById(Long id);
}
