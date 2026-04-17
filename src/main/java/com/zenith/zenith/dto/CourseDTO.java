package com.zenith.zenith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private UUID id;
    private String title;
    private String description;
    private List<NodeDTO> nodes;
    private List<EdgeDTO> edges;
}