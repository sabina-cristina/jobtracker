package com.sabina.jobtracker.service;

import com.sabina.jobtracker.model.Company;

import java.util.List;

public interface CompanyService {

    Company saveCompany(Company company);

    List<Company> getAllCompanies();

}