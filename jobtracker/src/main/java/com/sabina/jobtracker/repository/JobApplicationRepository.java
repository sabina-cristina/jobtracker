package com.sabina.jobtracker.repository;

import com.sabina.jobtracker.model.ApplicationStatus;
import com.sabina.jobtracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // Spring va scrie automat query-ul SQL pentru tine!
    List<JobApplication> findByCompanyId(Long companyId);

    // Noua metodă pentru filtrarea după status
    List<JobApplication> findByStatus(ApplicationStatus status);
}