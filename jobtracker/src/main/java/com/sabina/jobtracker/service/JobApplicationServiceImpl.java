package com.sabina.jobtracker.service;

import com.sabina.jobtracker.dto.JobApplicationRequest;
import com.sabina.jobtracker.model.Company;
import com.sabina.jobtracker.model.JobApplication;
import com.sabina.jobtracker.repository.CompanyRepository;
import com.sabina.jobtracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;

    // Injectăm ambele repozitoare prin constructor
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, CompanyRepository companyRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public JobApplication createJobApplication(JobApplicationRequest request) {
        // 1. Căutăm compania în baza de date. Dacă nu există, aruncăm o eroare.
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + request.getCompanyId()));

        // 2. Mapăm datele din Request DTO în entitatea JobApplication
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobTitle(request.getJobTitle());
        jobApplication.setStatus(request.getStatus());
        jobApplication.setJobDescriptionUrl(request.getJobDescriptionUrl());
        jobApplication.setCompany(company); // Legăm jobul de companie!

        // 3. Salvăm în baza de date
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public JobApplication updateJobStatus(Long jobId, com.sabina.jobtracker.model.ApplicationStatus newStatus) {
        // Găsim jobul sau aruncăm eroare
        JobApplication job = jobApplicationRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

        // Actualizăm statusul
        job.setStatus(newStatus);

        // Salvăm modificarea
        return jobApplicationRepository.save(job);
    }

    @Override
    public List<JobApplication> getJobsByCompany(Long companyId) {
        return jobApplicationRepository.findByCompanyId(companyId);
    }

    @Override
    public void deleteJobApplication(Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new RuntimeException("Job not found with id: " + id);
        }
        jobApplicationRepository.deleteById(id);
    }

    @Override
    public List<JobApplication> getJobsByStatus(com.sabina.jobtracker.model.ApplicationStatus status) {
        return jobApplicationRepository.findByStatus(status);
    }

    @Override
    public java.util.Map<com.sabina.jobtracker.model.ApplicationStatus, Long> getJobStatistics() {
        // Luăm toate joburile din baza de date
        List<JobApplication> allJobs = jobApplicationRepository.findAll();

        // Le grupăm după status și le numărăm
        return allJobs.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        JobApplication::getStatus,
                        java.util.stream.Collectors.counting()
                ));
    }
}