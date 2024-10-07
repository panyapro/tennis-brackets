package kz.pavershin.dto.response;

import kz.pavershin.dto.MatchDTO;
import kz.pavershin.dto.ParticipantDTO;
import kz.pavershin.dto.PlacementDTO;
import kz.pavershin.model.Match;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TournamentResponseDTO {
    private Integer id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<ParticipantDTO> participants;
    private List<PlacementDTO> placements;
    private List<MatchDTO> matches;
}

