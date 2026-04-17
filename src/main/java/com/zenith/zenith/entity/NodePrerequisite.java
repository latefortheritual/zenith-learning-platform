package com.zenith.zenith.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "node_prerequisites")
public class NodePrerequisite {

    @EmbeddedId
    private NodePrerequisiteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sourceNodeId")
    @JoinColumn(name = "source_node_id")
    private KnowledgeNode sourceNode;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("targetNodeId")
    @JoinColumn(name = "target_node_id")
    private KnowledgeNode targetNode;
}