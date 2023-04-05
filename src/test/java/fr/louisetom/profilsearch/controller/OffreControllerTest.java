package fr.louisetom.profilsearch.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;


import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.service.OffreService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OffreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OffreService offreService;
    @BeforeEach
    public void setUp() {
        offreService = mock(OffreService.class);
        mockMvc = standaloneSetup(new OffreController(offreService)).build();
    }


    @Test
    public void testCreateOffre() throws Exception {
        // Création d'un objet Offre
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Convertir l'objet Offre en JSON
        String offreJson = new ObjectMapper().writeValueAsString(offre);

        // Simulation de l'appel de la méthode createOffre du service OffreService
        when(offreService.createOffre(any(Offre.class))).thenReturn(offre);

        // Envoi d'une requête POST pour créer une nouvelle offre
        mockMvc.perform(post("/profilsearch/offre/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offreJson.getBytes()))
                .andExpect(status().isOk());

        // Vérification que la méthode createOffre du service OffreService a été appelée une fois avec n'importe quelle instance d'Offre
        verify(offreService, times(1)).createOffre(any(Offre.class));
    }



    @Test
    public void testGetAllOffre() throws Exception {
        // Creation de 3 offres
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        Offre offre2 = new Offre("Dev Python", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        Offre offre3 = new Offre("Dev C++", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Simulation de l'appel de la methode getAllOffre du service OffreService
        when(offreService.getAllOffre()).thenReturn(List.of(offre, offre2, offre3));

        // Envoie d'une requete GET pour recuperer toutes les offres
        mockMvc.perform(get("/profilsearch/offre/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Dev Java")))
                .andExpect(jsonPath("$[1].name", is("Dev Python")))
                .andExpect(jsonPath("$[2].name", is("Dev C++")));

        // Verification que la methode getAllOffre a bien ete appelee 1 seule fois
        verify(offreService, times(1)).getAllOffre();
    }

    @Test
    public void testGetOffreById() throws Exception {
        // Creation d'une offre
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Simulation de l'appel de la methode getOffreById du service OffreService
        when(offreService.getOffreById(anyLong())).thenReturn(Optional.of(offre));

        // Envoie d'une requete GET pour recuperer une offre par son id
        mockMvc.perform(get("/profilsearch/offre/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dev Java")));

        // Verification que la methode getOffreById a bien ete appelee 1 seule fois
        verify(offreService, times(1)).getOffreById(anyLong());
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        // Simulation de l'appel de la methode getOffreById du service OffreService
        when(offreService.getOffreById(anyLong())).thenReturn(Optional.empty());

        // Envoie d'une requete GET pour recuperer une offre par son id qui n'existe pas
        mockMvc.perform(get("/profilsearch/offre/3"))
                .andExpect(status().isNotFound());
    }
}
