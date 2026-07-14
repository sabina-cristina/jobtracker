package com.sabina.jobtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interview_notes")
public class InterviewNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    private LocalDateTime createdAt;

    // Relație Many-to-One: Multe notițe aparțin unui singur Job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id", nullable = false)
    @JsonIgnore // Important! Previne o buclă infinită când Spring transformă datele în JSON
    private JobApplication jobApplication;

    public InterviewNote() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Salvăm data și ora exactă
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(JobApplication jobApplication) {
        this.jobApplication = jobApplication;
    }
}