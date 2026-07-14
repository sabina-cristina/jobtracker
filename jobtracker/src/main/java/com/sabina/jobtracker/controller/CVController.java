package com.sabina.jobtracker.controller;

import com.sabina.jobtracker.model.JobApplication;
import com.sabina.jobtracker.repository.JobApplicationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/jobs/{jobId}/cv")
public class CVController {

    private final JobApplicationRepository jobRepository;

    public CVController(JobApplicationRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // 1. ÎNCĂRCARE CV (Upload)
    @PostMapping
    public ResponseEntity<String> uploadCV(@PathVariable Long jobId, @RequestParam("file") MultipartFile file) {
        JobApplication job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job-ul cu ID-ul " + jobId + " nu a fost găsit."));

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Fișierul selectat este gol!");
        }

        try {
            job.setCvFile(file.getBytes());
            job.setCvFileName(file.getOriginalFilename());
            job.setCvContentType(file.getContentType());
            jobRepository.save(job);

            return ResponseEntity.ok("CV-ul a fost încărcat cu succes!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare tehnică la citirea fișierului.");
        }
    }

    // 2. DESCARCARE CV (Download)
    @GetMapping
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long jobId) {
        JobApplication job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job-ul cu ID-ul " + jobId + " nu a fost găsit."));

        if (job.getCvFile() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(job.getCvContentType()))
                // Forțează browserul să descarce fișierul cu numele lui original salvat în bază
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + job.getCvFileName() + "\"")
                .body(job.getCvFile());
    }

    // 3. ȘTERGERE CV (Delete)
    @DeleteMapping
    public ResponseEntity<String> deleteCV(@PathVariable Long jobId) {
        JobApplication job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job-ul cu ID-ul " + jobId + " nu a fost găsit."));

        job.setCvFile(null);
        job.setCvFileName(null);
        job.setCvContentType(null);
        jobRepository.save(job);

        return ResponseEntity.ok("CV-ul a fost șters cu succes.");
    }
}