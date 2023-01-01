package com.jee.faang.controllers;

import com.jee.faang.models.metier.*;
import com.jee.faang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    AdminRepo adminRepo;

    @GetMapping("page")
    public List<Company> getAllCompaniesbyPage(){
        List<Company> companies = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 10);
        //System.out.println(" = " + adminRepo.findById(1L).get().getFullName());
        companyRepo.findAll(pageable).forEach((company -> {
            Company company1 = new Company(company.getId(),company.getNom(),company.getDescription(),company.getOpenDate(),company.getCloseDate());
            companies.add(company1);
        }));
        return companies;
    }
}
