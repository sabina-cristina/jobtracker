package com.sabina.jobtracker.service;

import com.sabina.jobtracker.dto.JobApplicationRequest;
import com.sabina.jobtracker.model.JobApplication;

import java.util.List;

public interface JobApplicationService {
    JobApplication createJobApplication(JobApplicationRequest request);
    List<JobApplication> getAllJobApplications();
}