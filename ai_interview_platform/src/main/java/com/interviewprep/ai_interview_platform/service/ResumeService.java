package com.interviewprep.ai_interview_platform.service;

import com.interviewprep.ai_interview_platform.model.Resume;
import com.interviewprep.ai_interview_platform.model.User;
import com.interviewprep.ai_interview_platform.repository.ResumeRepository;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserService userService;
    private final Tika tika;

    public ResumeService(ResumeRepository resumeRepository, UserService userService) {
        this.resumeRepository = resumeRepository;
        this.userService = userService;
        this.tika = new Tika();
    }

    public Resume uploadResume(String username, MultipartFile file) throws IOException, TikaException {
        User user = userService.getUserByUsername(username);
        
        // Check file type
        String contentType = tika.detect(file.getInputStream());
        if (!isValidFileType(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Only PDF and DOCX files are allowed.");
        }

        // Create new resume entity
        Resume resume = new Resume();
        resume.setUser(user);
        resume.setFileName(file.getOriginalFilename());
        resume.setFileType(contentType);
        resume.setFileSize(file.getSize());
        
        // Parse content
        String parsedContent = tika.parseToString(file.getInputStream());
        resume.setParsedContent(parsedContent);
        resume.setLastParsedAt(LocalDateTime.now());

        // Store original content as bytes converted to base64 string
        resume.setContent(new String(file.getBytes()));

        return resumeRepository.save(resume);
    }

    public List<Resume> getUserResumes(String username) {
        User user = userService.getUserByUsername(username);
        return resumeRepository.findByUser(user);
    }

    public Resume getLatestResume(String username) {
        User user = userService.getUserByUsername(username);
        return resumeRepository.findTopByUserOrderByUploadedAtDesc(user)
                .orElseThrow(() -> new IllegalStateException("No resume found for user: " + username));
    }

    private boolean isValidFileType(String contentType) {
        return contentType.equals("application/pdf") ||
            contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }
}
