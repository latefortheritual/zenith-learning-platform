package com.zenith.zenith.repository;

import com.zenith.zenith.entity.UserNodeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserNodeStateRepository extends JpaRepository<UserNodeState, UUID> {
    Optional<UserNodeState> findByUserIdAndNodeId(UUID userId, UUID nodeId);
    List<UserNodeState> findByUserIdAndNodeCourseId(UUID userId, UUID courseId);
}