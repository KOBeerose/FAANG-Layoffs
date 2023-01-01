package com.jee.faang.repository;

import com.jee.faang.models.metier.KeyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyWordRepo extends JpaRepository<KeyWord, Long> {
}
