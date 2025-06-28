package com.interviewprep.ai_interview_platform.repository;

import com.interviewprep.ai_interview_platform.model.Resume;
import com.interviewprep.ai_interview_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUser(User user);
    Optional<Resume> findTopByUserOrderByUploadedAtDesc(User user);
    boolean existsByUserAndFileName(User user, String fileName);
}
