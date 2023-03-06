package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Question;
import java.util.List;

public interface QuestionService {
    Question createQuestion(Question question);
    List<Question> getAllQuestion();
    Question getQuestionById(Long id);
}
