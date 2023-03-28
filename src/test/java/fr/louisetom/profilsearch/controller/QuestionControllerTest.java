package fr.louisetom.profilsearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.louisetom.profilsearch.model.Question;
import fr.louisetom.profilsearch.service.QuestionService;
import static org.hamcrest.Matchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuestionService questionService;

    @BeforeEach
    public void setUp() {
        questionService = mock(QuestionService.class);
        mockMvc = standaloneSetup(new QuestionController(questionService)).build();
    }

    @Test
    public void testCreateQuestion() throws Exception {
        Question question = new Question("Tu aimes le Java ?");
        String questionJson  = new ObjectMapper().writeValueAsString(question);
        when(questionService.createQuestion(any(Question.class))).thenReturn(question);

        mockMvc.perform(post("/profilsearch/question/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionJson.getBytes()))
                        .andExpect(status().isOk());

        verify(questionService, times(1)).createQuestion(any(Question.class));
    }

    @Test
    public void testGetAllQuestion() throws Exception {
        Question question = new Question("Tu aimes le Java ?");
        Question question2 = new Question("Tu aimes le Python ?");
        Question question3 = new Question("Tu aimes le C++ ?");
        when(questionService.getAllQuestion()).thenReturn(List.of(question, question2, question3));

        mockMvc.perform(get("/profilsearch/question/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question", is("Tu aimes le Java ?")))
                .andExpect(jsonPath("$[1].question", is("Tu aimes le Python ?")))
                .andExpect(jsonPath("$[2].question", is("Tu aimes le C++ ?")));

        verify(questionService, times(1)).getAllQuestion();
    }
}
