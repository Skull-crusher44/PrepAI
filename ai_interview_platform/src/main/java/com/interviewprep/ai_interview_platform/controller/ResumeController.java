package com.interviewprep.ai_interview_platform.controller;

import com.interviewprep.ai_interview_platform.model.Resume;
import com.interviewprep.ai_interview_platform.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        try {
            Resume resume = resumeService.uploadResume(authentication.getName(), file);
            return ResponseEntity.ok(Map.of(
                "message", "Resume uploaded successfully",
                "resumeId", resume.getId(),
                "fileName", resume.getFileName()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to upload resume",
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserResumes(Authentication authentication) {
        try {
            List<Resume> resumes = resumeService.getUserResumes(authentication.getName());
            return ResponseEntity.ok(resumes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to fetch resumes",
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestResume(Authentication authentication) {
        try {
            Resume resume = resumeService.getLatestResume(authentication.getName());
            return ResponseEntity.ok(resume);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to fetch latest resume",
                "message", e.getMessage()
            ));
        }
    }
}
