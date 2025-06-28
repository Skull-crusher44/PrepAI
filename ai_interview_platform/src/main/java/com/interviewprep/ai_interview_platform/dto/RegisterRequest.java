package com.interviewprep.ai_interview_platform.dto;

// A record is an immutable data carrier class.
// It automatically creates a constructor, getters, equals(), hashCode(), and toString().
public record RegisterRequest(String username, String email, String password) {
}
