package kz.pavershin.repository;

import kz.pavershin.model.Participant;
import kz.pavershin.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository  extends JpaRepository<Participant, Long> {

    Optional<Participant> findByName(String name);
}
