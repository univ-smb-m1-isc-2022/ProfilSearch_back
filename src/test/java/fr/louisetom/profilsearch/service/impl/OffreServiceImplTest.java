package fr.louisetom.profilsearch.service.impl;

import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.repository.OffreRepository;
import fr.louisetom.profilsearch.service.OffreService;
import fr.louisetom.profilsearch.service.OffreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OffreServiceImplTest {
    private OffreService offreService;

    private OffreRepository offreRepo;

    @BeforeEach
    public void setUp() {
        offreRepo = mock(OffreRepository.class);

        offreService = new OffreServiceImpl(offreRepo);
    }

    @Test
    public void testCreateOffre() {
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        when(offreRepo.save(offre)).thenReturn(offre);
        Offre offreCreated = offreService.createOffre(offre);
        assertThat(offreCreated).isEqualTo(offre);
    }

    @Test
    public void testGetAllOffre() {
        // Création de 3 offres
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        Offre offre2 = new Offre("Dev Python", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        Offre offre3 = new Offre("Dev C++", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Configuration du mock
        when(offreRepo.findAll()).thenReturn(List.of(offre, offre2, offre3));

        // Appel de la méthode à tester
        List<Offre> result = offreService.getAllOffre();

        // Vérification du résultat
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrder(offre, offre2, offre3);
    }

    @Test
    public void testGetOffreById() {
        // Création d'une offre
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Configuration du mock du repository pour retourner l'offre créée
        when(offreRepo.findById(anyLong())).thenReturn(Optional.of(offre));

        // Appel de la méthode getOffreById du service
        Optional<Offre> result = offreService.getOffreById(1L);

        // Vérification que la méthode a renvoyé une offre non vide
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(offre);

        // Vérification que la méthode findById du repository a bien été appelée une seule fois
        verify(offreRepo, times(1)).findById(anyLong());
    }

    @Test
    public void testGetOffreByIdNotFound() {
        // Configuration du mock du repository pour retourner une offre vide
        when(offreRepo.findById(anyLong())).thenReturn(Optional.empty());

        // Appel de la méthode getOffreById du service
        Optional<Offre> result = offreService.getOffreById(1L);

        // Vérification que la méthode a renvoyé une offre vide
        assertThat(result.isPresent()).isFalse();

        // Vérification que la méthode findById du repository a bien été appelée une seule fois
        verify(offreRepo, times(1)).findById(anyLong());
    }
}
