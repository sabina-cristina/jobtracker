package com.sabina.jobtracker.controller;

import com.sabina.jobtracker.dto.JobApplicationRequest;
import com.sabina.jobtracker.model.JobApplication;
import com.sabina.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping
    public ResponseEntity<JobApplication> createJob(@Valid @RequestBody JobApplicationRequest request) {
        JobApplication savedJob = jobApplicationService.createJobApplication(request);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobs() {
        List<JobApplication> jobs = jobApplicationService.getAllJobApplications();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}