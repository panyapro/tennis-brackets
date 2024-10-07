package kz.pavershin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TournamentDTO {
    private Integer id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<ParticipantDTO> participants;
    private List<MatchDTO> matches;
    private List<PlacementDTO> placements;

    // Геттеры и сеттеры
}
