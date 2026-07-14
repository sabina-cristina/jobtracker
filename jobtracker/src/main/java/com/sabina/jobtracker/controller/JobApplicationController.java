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

    // Endpoint pentru actualizarea statusului (folosim PATCH pentru actualizări parțiale)
    @PatchMapping("/{id}/status")
    public ResponseEntity<JobApplication> updateStatus(
            @PathVariable Long id,
            @RequestParam com.sabina.jobtracker.model.ApplicationStatus status) {

        JobApplication updatedJob = jobApplicationService.updateJobStatus(id, status);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    // Endpoint pentru a vedea toate joburile de la o companie specifică
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobApplication>> getJobsByCompany(@PathVariable Long companyId) {
        List<JobApplication> jobs = jobApplicationService.getJobsByCompany(companyId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobApplicationService.deleteJobApplication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content este standardul pentru delete
    }
}