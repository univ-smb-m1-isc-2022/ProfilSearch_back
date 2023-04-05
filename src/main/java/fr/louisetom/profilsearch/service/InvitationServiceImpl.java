package fr.louisetom.profilsearch.service;

import fr.louisetom.profilsearch.model.Invitation;
import fr.louisetom.profilsearch.repository.InvitationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    public final InvitationRepository invitationRepository;

    @Override
    public Invitation createInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    @Override
    public List<Invitation> getAllInvitation() {
        return invitationRepository.findAll();
    }

    @Override
    public Optional<Invitation> getInvitationByEmail(String string) {
        return invitationRepository.findByEmail(string);
    }

    @Override
    public Invitation getInvitationById(Long id) {
        return invitationRepository.findById(id).orElse(null);
    }
}
