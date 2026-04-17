package com.zenith.zenith.repository;

import com.zenith.zenith.entity.NodePrerequisite;
import com.zenith.zenith.entity.NodePrerequisiteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NodePrerequisiteRepository
        extends JpaRepository<NodePrerequisite, NodePrerequisiteId> {

    List<NodePrerequisite> findByIdTargetNodeId(UUID targetNodeId);
    List<NodePrerequisite> findBySourceNodeCourseId(UUID courseId);
}