package fr.louisetom.profilsearch.repository;

import fr.louisetom.profilsearch.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    public Invitation findByEmail(String email);
}
