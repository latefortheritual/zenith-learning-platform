package com.zenith.zenith.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_node_states")
public class UserNodeState {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private KnowledgeNode node;

    @Column(name = "p_mastered", nullable = false)
    private Double pMastered;

    @Column(name = "is_mastered", nullable = false)
    private boolean mastered;

    @Column(name = "is_skipped", nullable = false)
    private boolean skipped;

    @Column(name = "last_visited_at")
    private LocalDateTime lastVisitedAt;
}