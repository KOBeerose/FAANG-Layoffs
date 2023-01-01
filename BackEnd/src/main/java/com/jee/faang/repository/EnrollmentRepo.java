package com.jee.faang.repository;

import com.jee.faang.models.metier.Company;
import com.jee.faang.models.metier.Enrollment;
import com.jee.faang.models.metier.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByGuest(Guest guest);
    List<Enrollment> deleteAllByCompany(Company company);
    List<Enrollment> deleteAllByGuest(Guest guest);
}
