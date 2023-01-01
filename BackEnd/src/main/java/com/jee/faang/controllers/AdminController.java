package com.jee.faang.controllers;

import com.jee.faang.models.metier.Guest;
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
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    GuestRepo guestRepo;

    @Autowired
    EnrollmentRepo enrollmentRepo;


    @GetMapping("employees")
    public List<Employee> getAlEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepo.findAll().forEach(employee -> {
            Employee employee1 = new Employee(employee.getFullName(),employee.getUsername(),null,employee.getFullName());
            employee1.setId(employee.getId());
            employees.add(employee1);
        });
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username = " + username);
        Collection<? extends GrantedAuthority> userId = userDetails.getAuthorities();
        System.out.println("userId = " + userId);
        return employees;
        
    }

    @GetMapping("guests")
    public List<Guest> getAllGuests(){
        List<Guest> guests = new ArrayList<>();
        guestRepo.findAll().forEach(guest -> {
            Guest guest1 = new Guest(guest.getFullName(),guest.getUsername(),null,guest.getFullName());
            guest1.setId(guest.getId());
            guests.add(guest1);
        });
        return guests;
    }

    @Transactional
    @DeleteMapping("deleteguest/{id}")
    public void deleteGuest(@PathVariable Long id){
        enrollmentRepo.deleteAllByGuest(guestRepo.getById(id));
        guestRepo.deleteById(id);
    }

    @Transactional
    @DeleteMapping("deleteemployee/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeRepo.getById(id).getCompanies().forEach(company -> {
            enrollmentRepo.deleteAllByCompany(company);
        });
        companyRepo.deleteAllByOwner(employeeRepo.getById(id));
        employeeRepo.deleteById(id);
    }
}
