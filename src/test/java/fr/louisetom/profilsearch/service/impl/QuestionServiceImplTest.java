package fr.louisetom.profilsearch.service.impl;

import fr.louisetom.profilsearch.model.Question;
import fr.louisetom.profilsearch.repository.QuestionRepository;
import fr.louisetom.profilsearch.service.QuestionService;
import fr.louisetom.profilsearch.service.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuestionServiceImplTest {
    private QuestionService questionService;
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setUp() {
        questionRepository = mock(QuestionRepository.class);
        questionService = new QuestionServiceImpl(questionRepository);
    }

    @Test
    public void testCreateQuestion() {
        // Création de la question
        Question question = new Question("Quelle est ta couleur préférée ?");

        // Définition du comportement du mock
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        // Appel de la méthode à tester
        Question createdQuestion = questionService.createQuestion(question);

        // Vérification que la méthode save du repository a été appelée avec la question en paramètre
        verify(questionRepository, times(1)).save(eq(question));

        // Vérification que la question retournée est la même que celle passée en paramètre
        assertThat(createdQuestion).isSameAs(question);
    }

    @Test
    public void testGetAllQuestion() {
        // Création de 3 questions
        Question question = new Question("Quelle est ta couleur préférée ?");
        Question question2 = new Question("Quelle est ta couleur préférée ?");
        Question question3 = new Question("Quelle est ta couleur préférée ?");

        // Configuration du mock
        when(questionRepository.findAll()).thenReturn(List.of(question, question2, question3));

        // Appel de la méthode à tester
        List<Question> result = questionService.getAllQuestion();

        // Vérification du résultat
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrder(question, question2, question3);
    }

    @Test
    public void testGetQuestionById() {
        // Création d'une question
        Question question = new Question("Quelle est ta couleur préférée ?");

        // Configuration du mock du repository pour retourner la question créée
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));

        // Appel de la méthode à tester
        Question result = questionService.getQuestionById(1L);

        // Vérification du résultat
        assertThat(result).isSameAs(question);
    }

    /*@Test
    public void testGetQuestionByIdNotFound() {
        // Configuration du mock du repository pour retourner une Optional vide
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Appel de la méthode à tester
        Question result = questionService.getQuestionById(1L);

        // Vérification du résultat
        assertThat(result.isPresent()).isFalse();
    }*/
}
