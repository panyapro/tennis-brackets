package kz.pavershin.dto;

import kz.pavershin.model.BracketType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MatchDTO {
    private Integer id;
    private BracketType bracketType;
    private Integer round;
    private Integer matchNumber;
    private ParticipantDTO player1;
    private ParticipantDTO player2;
    private ParticipantDTO winner;
    private ParticipantDTO loser;
    private Integer previousMatch1Id;
    private Integer previousMatch2Id;
    private LocalDateTime scheduledTime;
    private LocalDateTime completedTime;
    private Integer placementId; // Nullable

    // Геттеры и сеттеры
}
