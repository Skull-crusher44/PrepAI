package com.interviewprep.ai_interview_platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "Backend is up and running!";
    }
}