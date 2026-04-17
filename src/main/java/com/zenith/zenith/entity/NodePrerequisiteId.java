package com.zenith.zenith.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodePrerequisiteId implements Serializable {

    @Column(name = "source_node_id")
    private UUID sourceNodeId;

    @Column(name = "target_node_id")
    private UUID targetNodeId;
}