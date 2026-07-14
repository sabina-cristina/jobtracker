package com.sabina.jobtracker.controller;

import com.sabina.jobtracker.dto.InterviewNoteRequest;
import com.sabina.jobtracker.model.InterviewNote;
import com.sabina.jobtracker.model.JobApplication;
import com.sabina.jobtracker.repository.InterviewNoteRepository;
import com.sabina.jobtracker.repository.JobApplicationRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs/{jobId}/notes")
public class InterviewNoteController {

    private final InterviewNoteRepository noteRepository;
    private final JobApplicationRepository jobRepository;

    public InterviewNoteController(InterviewNoteRepository noteRepository, JobApplicationRepository jobRepository) {
        this.noteRepository = noteRepository;
        this.jobRepository = jobRepository;
    }

    // Adaugă o notiță nouă la un job existent
    @PostMapping
    public ResponseEntity<InterviewNote> addNote(@PathVariable Long jobId, @Valid @RequestBody InterviewNoteRequest request) {
        JobApplication job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job-ul cu ID-ul " + jobId + " nu a fost găsit."));

        InterviewNote note = new InterviewNote();
        note.setContent(request.getContent());
        note.setJobApplication(job);

        InterviewNote savedNote = noteRepository.save(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    // Ia toate notițele pentru un anumit job
    @GetMapping
    public ResponseEntity<List<InterviewNote>> getNotesForJob(@PathVariable Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new RuntimeException("Job-ul cu ID-ul " + jobId + " nu a fost găsit.");
        }

        List<InterviewNote> notes = noteRepository.findByJobApplicationIdOrderByCreatedAtDesc(jobId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
}