package fr.louisetom.profilsearch.service;


import fr.louisetom.profilsearch.model.Invitation;

import java.util.List;
import java.util.Optional;

public interface InvitationService {

    Invitation createInvitation(Invitation invitation);
    List<Invitation> getAllInvitation();
    Optional<Invitation> getInvitationByEmail(String string);

    Invitation getInvitationById(Long id);
}
