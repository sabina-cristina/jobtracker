package com.sabina.jobtracker.repository;

import com.sabina.jobtracker.model.InterviewNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewNoteRepository extends JpaRepository<InterviewNote, Long> {
    // Returnează toate notițele unui job, ordonate de la cea mai nouă la cea mai veche
    List<InterviewNote> findByJobApplicationIdOrderByCreatedAtDesc(Long jobApplicationId);
}