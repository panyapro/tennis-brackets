package kz.pavershin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "placements")
public class Placement extends BaseEntity {


    public Placement() {}

    public Placement(Tournament tournament, Integer placement, String description) {
        this.placement = placement;
    }

    private Integer placement; // Например, 17 для 17-32 места

    private String description; // "17-32 место"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Column(columnDefinition = "TIMESTAMP",name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP",name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "placement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matches;

    // Геттеры и сеттеры

    // Конструкторы, хэш-коды и equals
}
