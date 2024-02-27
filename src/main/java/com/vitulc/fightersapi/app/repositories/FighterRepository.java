package com.vitulc.fightersapi.app.repositories;

import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FighterRepository extends JpaRepository<Fighter, Long> {
    boolean existsByUserAndDocumentIgnoreCase(Users user, String document);
    Optional<Fighter> findByUserAndDocument(Users user, String document);
}
