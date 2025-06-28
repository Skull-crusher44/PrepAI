package com.interviewprep.ai_interview_platform.dto;

public record LoginResponse(
    String token,
    String username,
    String email
) {}
