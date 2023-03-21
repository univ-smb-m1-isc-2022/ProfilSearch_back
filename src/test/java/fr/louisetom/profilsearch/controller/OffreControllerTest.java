package fr.louisetom.profilsearch.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.louisetom.profilsearch.controller.OffreController;
import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.service.OffreService;

import java.util.Date;


@SpringBootTest
@AutoConfigureMockMvc
public class OffreControllerTest {

    @Autowired
    public MockMvc mockMvc;



    @Test
    public void testGetAllOffre() throws Exception {
        mockMvc.perform(get("/profilsearch/offre/all"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is("Dev Java")));
    }


    /*public void createOffreTest() throws Exception {
        // Créer un objet Offre
        Offre offre = new Offre("Dev Java", new Date(), "Description", "CDI", "Paris", 3000, null);

        // Simuler l'ajout de l'offre à la base de données
        when(offreService.createOffre(offre)).thenReturn(offre);

        // Convertir l'objet Offre en JSON
        String offreJson = new ObjectMapper().writeValueAsString(offre);

        // Envoyer une requête HTTP POST pour créer une nouvelle offre
        mockMvc.perform(post("/profilsearch/offre/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offreJson.getBytes()))

                // Vérifier que la requête a retourné un code HTTP 200
                .andExpect(status().isOk())

                // Vérifier que l'objet Offre créé a les mêmes attributs que ceux spécifiés dans la méthode createOffreTest()
                .andExpect(jsonPath("$.name").value("Dev Java"))
                .andExpect(jsonPath("$.city").value("Paris"))
                .andExpect(jsonPath("$.salary").value(3000));
    }*/
}
