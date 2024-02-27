package com.vitulc.fightersapi.app.repositories;

import com.vitulc.fightersapi.app.entities.Tournament;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<Tournament> findByUserAndId(Users users, Long id);
}
