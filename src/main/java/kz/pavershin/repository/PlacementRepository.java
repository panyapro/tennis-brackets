package kz.pavershin.repository;

import kz.pavershin.model.Placement;
import kz.pavershin.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacementRepository  extends JpaRepository<Placement, Long> {

    List<Placement> findByTournamentId(Long tournamentId);
}
