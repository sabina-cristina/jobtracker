package com.sabina.jobtracker.dto;

import com.sabina.jobtracker.model.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JobApplicationRequest {

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private String jobDescriptionUrl;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    public JobApplicationRequest() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getJobDescriptionUrl() {
        return jobDescriptionUrl;
    }

    public void setJobDescriptionUrl(String jobDescriptionUrl) {
        this.jobDescriptionUrl = jobDescriptionUrl;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}