package com.sabina.jobtracker.controller;

import com.sabina.jobtracker.model.Company;
import com.sabina.jobtracker.model.JobApplication;
import com.sabina.jobtracker.model.ApplicationStatus; // <--- Importăm enum-ul tău corect
import com.sabina.jobtracker.model.User;
import com.sabina.jobtracker.repository.CompanyRepository;
import com.sabina.jobtracker.repository.JobApplicationRepository;
import com.sabina.jobtracker.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    private final JobApplicationRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public JobApplicationController(JobApplicationRepository jobRepository,
                                    CompanyRepository companyRepository,
                                    UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilizatorul curent nu a fost găsit."));
    }

    // 1. SALVARE JOB NOU
    @PostMapping
    public ResponseEntity<JobApplication> createJob(@RequestBody Map<String, Object> payload, Principal principal) {
        User currentUser = getLoggedInUser(principal);

        String jobTitle = (String) payload.get("jobTitle");
        // Folosim ApplicationStatus în loc de JobStatus
        ApplicationStatus status = ApplicationStatus.valueOf((String) payload.get("status"));
        String jobDescriptionUrl = (String) payload.get("jobDescriptionUrl");
        Long companyId = Long.valueOf(payload.get("companyId").toString());

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Compania nu a fost găsită."));

        JobApplication job = new JobApplication();
        job.setJobTitle(jobTitle);
        job.setStatus(status);
        job.setJobDescriptionUrl(jobDescriptionUrl);
        job.setCompany(company);
        job.setAppliedAt(LocalDate.now());
        job.setUser(currentUser);

        JobApplication savedJob = jobRepository.save(job);
        return ResponseEntity.ok(savedJob);
    }

    // 2. STATISTICI
    @GetMapping("/statistics")
    public ResponseEntity<Map<ApplicationStatus, Long>> getStatistics(Principal principal) {
        User currentUser = getLoggedInUser(principal);
        List<JobApplication> userJobs = jobRepository.findByUser(currentUser);

        Map<ApplicationStatus, Long> stats = new HashMap<>();
        for (ApplicationStatus status : ApplicationStatus.values()) {
            stats.put(status, 0L);
        }

        for (JobApplication job : userJobs) {
            stats.put(job.getStatus(), stats.get(job.getStatus()) + 1);
        }

        return ResponseEntity.ok(stats);
    }

    // 3. FILTRARE JOBURI
    @GetMapping("/filter")
    public ResponseEntity<List<JobApplication>> getJobsByStatus(@RequestParam ApplicationStatus status, Principal principal) {
        User currentUser = getLoggedInUser(principal);
        List<JobApplication> jobs = jobRepository.findByUserAndStatus(currentUser, status);
        return ResponseEntity.ok(jobs);
    }
}