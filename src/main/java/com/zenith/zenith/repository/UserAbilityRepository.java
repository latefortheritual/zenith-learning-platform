package com.zenith.zenith.repository;

import com.zenith.zenith.entity.UserAbility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAbilityRepository extends JpaRepository<UserAbility, UUID> {
    Optional<UserAbility> findByUserIdAndCourseId(UUID userId, UUID courseId);
}