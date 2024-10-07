package kz.pavershin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "participant_losses")
public class ParticipantLoss extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_participant_id", nullable = false)
    private TournamentParticipant tournamentParticipant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(columnDefinition = "TIMESTAMP",name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Геттеры и сеттеры

    // Конструкторы, хэш-коды и equals
}
