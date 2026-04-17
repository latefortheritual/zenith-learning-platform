package com.zenith.zenith.repository;

import com.zenith.zenith.entity.UserCourseEnrollment;
import com.zenith.zenith.entity.UserCourseEnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseEnrollmentRepository
        extends JpaRepository<UserCourseEnrollment, UserCourseEnrollmentId> {
}