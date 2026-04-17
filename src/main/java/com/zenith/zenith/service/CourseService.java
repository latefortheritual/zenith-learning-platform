package com.zenith.zenith.service;

import com.zenith.zenith.dto.*;
import com.zenith.zenith.entity.*;
import com.zenith.zenith.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final KnowledgeNodeRepository nodeRepository;
    private final NodePrerequisiteRepository prerequisiteRepository;
    private final UserNodeStateRepository nodeStateRepository;
    private final UserCourseEnrollmentRepository enrollmentRepository;
    private final UserAbilityRepository abilityRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(course -> CourseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseDTO getCourseWithGraph(UUID courseId, UUID userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        List<KnowledgeNode> nodes = nodeRepository.findByCourseId(courseId);
        List<NodePrerequisite> prerequisites = prerequisiteRepository
                .findBySourceNodeCourseId(courseId);

        // Load student node states if enrolled
        Map<UUID, UserNodeState> stateMap = new HashMap<>();
        boolean enrolled = enrollmentRepository.existsById(
                new UserCourseEnrollmentId(userId, courseId));

        if (enrolled) {
            nodeStateRepository.findByUserIdAndNodeCourseId(userId, courseId)
                    .forEach(state -> stateMap.put(state.getNode().getId(), state));
        }

        // Build set of mastered node IDs for status computation
        Set<UUID> masteredIds = stateMap.values().stream()
                .filter(UserNodeState::isMastered)
                .map(s -> s.getNode().getId())
                .collect(Collectors.toSet());

        // Build map: nodeId -> list of prerequisite source node IDs
        Map<UUID, List<UUID>> prereqMap = new HashMap<>();
        for (NodePrerequisite p : prerequisites) {
            prereqMap
                    .computeIfAbsent(p.getId().getTargetNodeId(), k -> new ArrayList<>())
                    .add(p.getId().getSourceNodeId());
        }

        // Convert nodes to DTOs with computed status
        List<NodeDTO> nodeDTOs = nodes.stream()
                .map(node -> {
                    NodeStatus status = null;
                    if (enrolled) {
                        UserNodeState state = stateMap.get(node.getId());
                        if (state != null && state.isMastered()) {
                            status = NodeStatus.MASTERED;
                        } else {
                            List<UUID> prereqs = prereqMap.getOrDefault(
                                    node.getId(), Collections.emptyList());
                            boolean allPrereqsMastered = prereqs.isEmpty() ||
                                    masteredIds.containsAll(prereqs);
                            status = allPrereqsMastered
                                    ? NodeStatus.AVAILABLE
                                    : NodeStatus.LOCKED;
                        }
                    }
                    return NodeDTO.builder()
                            .id(node.getId())
                            .title(node.getTitle())
                            .description(node.getDescription())
                            .positionX(node.getPositionX())
                            .positionY(node.getPositionY())
                            .status(status)
                            .build();
                })
                .collect(Collectors.toList());

        // Convert edges to DTOs
        List<EdgeDTO> edgeDTOs = prerequisites.stream()
                .map(p -> EdgeDTO.builder()
                        .sourceNodeId(p.getId().getSourceNodeId())
                        .targetNodeId(p.getId().getTargetNodeId())
                        .build())
                .collect(Collectors.toList());

        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .nodes(nodeDTOs)
                .edges(edgeDTOs)
                .build();
    }

    @Transactional
    public void enrollStudent(UUID courseId, UUID userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        UserCourseEnrollmentId enrollmentId =
                new UserCourseEnrollmentId(userId, courseId);

        if (enrollmentRepository.existsById(enrollmentId)) {
            return; // already enrolled, do nothing
        }

        // Create enrollment
        User user = new User();
        user.setId(userId);

        enrollmentRepository.save(UserCourseEnrollment.builder()
                .id(enrollmentId)
                .user(user)
                .course(course)
                .build());

        // Initialize theta = 0.0
        abilityRepository.save(UserAbility.builder()
                .user(user)
                .course(course)
                .theta(0.0)
                .build());

        // Initialize node states for all nodes
        List<KnowledgeNode> nodes = nodeRepository.findByCourseId(courseId);
        List<UserNodeState> states = nodes.stream()
                .map(node -> UserNodeState.builder()
                        .user(user)
                        .node(node)
                        .pMastered(0.10)
                        .mastered(false)
                        .skipped(false)
                        .build())
                .collect(Collectors.toList());

        nodeStateRepository.saveAll(states);
    }
}