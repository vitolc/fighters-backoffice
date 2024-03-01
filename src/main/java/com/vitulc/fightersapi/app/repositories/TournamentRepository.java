package com.vitulc.fightersapi.app.repositories;

import com.vitulc.fightersapi.app.dtos.FighterResponseDto;
import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.entities.Tournament;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<Tournament> findByUserAndId(Users user, Long id);
    Optional<Tournament> findByUserAndName(Users user, String name);
    List<Tournament> findByUser(Users user);
}
