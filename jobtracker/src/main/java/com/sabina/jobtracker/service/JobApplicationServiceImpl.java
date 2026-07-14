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
}