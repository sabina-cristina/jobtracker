package com.sabina.jobtracker.repository;

import com.sabina.jobtracker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}