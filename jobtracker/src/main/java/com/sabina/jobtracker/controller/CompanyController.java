package com.sabina.jobtracker.controller;

import com.sabina.jobtracker.dto.CompanyRequest;
import com.sabina.jobtracker.model.Company;
import com.sabina.jobtracker.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        // Mapare manuală de la DTO (CompanyRequest) la Entitate (Company)
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setLocation(companyRequest.getLocation());
        company.setWebsite(companyRequest.getWebsite());

        Company savedCompany = companyService.saveCompany(company);

        // Returnează obiectul salvat și statusul HTTP 201 Created
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();

        // Returnează lista de companii și statusul HTTP 200 OK
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
}