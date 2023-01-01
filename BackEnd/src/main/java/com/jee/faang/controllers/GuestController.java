package com.jee.faang.controllers;

import com.jee.faang.models.metier.Company;
import com.jee.faang.models.metier.Enrollment;
import com.jee.faang.models.metier.Guest;
import com.jee.faang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/guest")
@PreAuthorize("hasRole('GUEST')")
public class GuestController {
    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GuestRepo guestRepo;

    @Autowired
    EnrollmentRepo enrollmentRepo;

    /**
     * get enrollment list by guest
    */
    @GetMapping("enrollments/{id}")
    public List<Enrollment> getEnrollmentsByGuest(@PathVariable Long id) {
        Guest guest = guestRepo.getById(id);
        List<Enrollment> enrollmentList = new ArrayList<>();
        enrollmentRepo.findAllByGuest(guest).forEach(enrollment -> {
            Company company = enrollment.getCompany();
            Company company1 = new Company(company.getId(),company.getNom(),company.getDescription(),company.getOpenDate(),company.getCloseDate());
            Enrollment enrollment1 = new Enrollment();
            enrollment1.setId(enrollment.getId());
            enrollment1.setEnrollmentDate(enrollment.getEnrollmentDate());
            enrollment1.setCompany(company1);
            enrollmentList.add(enrollment1);
        });
        return enrollmentList;
    }

    /**
     * get All the available companies
     */
    @GetMapping("companieslist")
    public List<Company> getAllCompanies(){
        List<Company> companies = new ArrayList<>();
        companyRepo.findAll().forEach((company -> {
            Company company1 = new Company(company.getId(),company.getNom(),company.getDescription(),company.getOpenDate(),company.getCloseDate());
            companies.add(company1);
        }));
        return companies;
    }

    /**
     * enroll a company by a guest
     */
    @PostMapping("enrollcompany/{idGuest}")
    public void enrollCompany(@PathVariable Long idGuest, @RequestBody Company company){
        Enrollment enrollment = new Enrollment(null,guestRepo.getById(idGuest),companyRepo.getById(company.getId()), LocalDate.now());
        enrollmentRepo.save(enrollment);

    }
}
