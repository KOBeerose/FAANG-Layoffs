package com.jee.faang.controllers;

import com.jee.faang.models.metier.Company;
import com.jee.faang.models.metier.Employee;
import com.jee.faang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {
    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EnrollmentRepo enrollmentRepo;

    @GetMapping("list/{id}")
    public List<Company> getCompaniesByEmployee(@PathVariable Long id){
        List<Company> companies = new ArrayList<>();
        companyRepo.findAllByOwner(employeeRepo.getById(id)).forEach((company -> {
            Company company1 = new Company(company.getId(),company.getNom(),company.getDescription(),company.getOpenDate(),company.getCloseDate());
            companies.add(company1);
        }));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username = " + username);
        Collection<? extends GrantedAuthority> userId = userDetails.getAuthorities();
        System.out.println("userId = " + userId);
        return companies;
    }

    @PostMapping("addcompany/{id}")
    public Company addCompany(@RequestBody Company company,@PathVariable Long id){
        Employee employee = employeeRepo.getById(id);
        Company companyToAdd = new Company(null,company.getNom(),company.getDescription(),company.getOpenDate(),company.getCloseDate(),null,employee);
        companyRepo.save(companyToAdd);
        return new Company(companyToAdd.getId(),companyToAdd.getNom(),companyToAdd.getDescription(),companyToAdd.getOpenDate(),companyToAdd.getCloseDate());
    }

    @Transactional
    @PutMapping("updatecompany/{id}")
    public Company updateCompany(@PathVariable Long id,@RequestBody Company company){
        System.out.println("id = " + id);
        System.out.println("company = " + company);
        Company companyToUpdate = companyRepo.findById(id).get();
        companyToUpdate.setNom(company.getNom());
        companyToUpdate.setDescription(company.getDescription());
        companyToUpdate.setOpenDate(company.getOpenDate());
        companyToUpdate.setCloseDate(company.getCloseDate());
        companyRepo.save(companyToUpdate);
        //companyToUpdate.setOwner(null);
        return new Company(companyToUpdate.getId(), companyToUpdate.getNom(), companyToUpdate.getDescription(),companyToUpdate.getOpenDate(),companyToUpdate.getCloseDate());
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    public String tryDeleteCompany(@PathVariable Long id){
        try {
            companyRepo.deleteById(id);
            return "true";
        }
        catch (Exception e) {
            return "false";
        }
    }

    @Transactional
    @DeleteMapping("deleteforced/{id}")
    public void forceDeleteCompany(@PathVariable Long id){
        enrollmentRepo.deleteAllByCompany(companyRepo.getById(id));
        companyRepo.deleteById(id);
    }
}
