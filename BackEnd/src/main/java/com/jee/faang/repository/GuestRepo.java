package com.jee.faang.repository;

import com.jee.faang.models.metier.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepo extends JpaRepository<Guest, Long> {
}
