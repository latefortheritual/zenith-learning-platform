package com.zenith.zenith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO {
    private UUID id;
    private String title;
    private String description;
    private Double positionX;
    private Double positionY;
    private NodeStatus status;
}