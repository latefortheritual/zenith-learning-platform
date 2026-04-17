package com.zenith.zenith.repository;

import com.zenith.zenith.entity.KnowledgeNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KnowledgeNodeRepository extends JpaRepository<KnowledgeNode, UUID> {
    List<KnowledgeNode> findByCourseId(UUID courseId);
}