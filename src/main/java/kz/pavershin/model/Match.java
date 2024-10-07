package kz.pavershin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "matches")
public class Match extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Enumerated(EnumType.STRING)
    @Column(name = "bracket_type")
    private BracketType bracketType; // ENUM: 'winners', 'losers', 'placement_final', 'final'

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_id")
    private Placement placement; // Nullable

    private Integer round;

    @Column(name = "match_number")
    private Integer matchNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player1_id")
    private TournamentParticipant player1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2_id")
    private TournamentParticipant player2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private TournamentParticipant winner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loser_id")
    private TournamentParticipant loser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_match1_id")
    private Match previousMatch1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_match2_id")
    private Match previousMatch2;

    @Column(columnDefinition = "TIMESTAMP",name = "scheduled_time")
    private LocalDateTime scheduledTime;

    @Column(columnDefinition = "TIMESTAMP",name = "completed_time")
    private LocalDateTime completedTime;

    @Column(columnDefinition = "TIMESTAMP",name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP",name = "updated_at")
    private LocalDateTime updatedAt;

    // Геттеры и сеттеры

    // Конструкторы, хэш-коды и equals
}
