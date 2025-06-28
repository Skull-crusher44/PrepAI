package com.interviewprep.ai_interview_platform.repository;

import com.interviewprep.ai_interview_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA will automatically create a query for this method
    // based on the method name.
    Optional<User> findByUsername(String username);
}