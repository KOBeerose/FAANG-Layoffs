package com.jee.faang.repository;

import com.jee.faang.models.metier.Company;
import com.jee.faang.models.metier.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

    Optional<Company> findById(Long id);
    List<Company> findAllByOwner(Employee employee);
    Page<Company> findAll(Pageable pageable);
    List<Company> deleteAllByOwner(Employee employee);
}
