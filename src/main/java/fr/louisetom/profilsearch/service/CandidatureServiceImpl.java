package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Candidature;
import fr.louisetom.profilsearch.repository.CandidatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidatureServiceImpl implements CandidatureService {
    public final CandidatureRepository candidatureRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Candidature createCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public List<Candidature> getAllCandidature() {
        return candidatureRepository.findAll();
    }

    @Override
    public Candidature getCandidatureById(Long id) {
        return candidatureRepository.findById(id).orElse(null);
    }

    @Override
    public List<Candidature> getCandidaturesByOffre(Long id) {
        return candidatureRepository.findAllByOffreId(id);
    }

    @Override
    public Candidature getCandidatureByToken(String token) {
        return candidatureRepository.findByToken(token);
    }

    @Override
    public void deleteCandidature(Candidature candidature) {
        candidatureRepository.delete(candidature);
    }

}
