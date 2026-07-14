package com.sabina.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;

public class InterviewNoteRequest {

    @NotBlank(message = "Notița nu poate fi goală")
    private String content;

    public InterviewNoteRequest() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}