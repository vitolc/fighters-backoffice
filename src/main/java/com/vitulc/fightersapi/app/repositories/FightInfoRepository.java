package com.vitulc.fightersapi.app.repositories;


import com.vitulc.fightersapi.app.entities.FightInfo;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FightInfoRepository extends JpaRepository<FightInfo, Long> {

    Optional<List<FightInfo>> findFightHistoryByUserOrderByIdDesc(Users user);

    Optional<List<FightInfo>> findFightHistoryByUserAndTournamentNameOrderByIdDesc(Users user, String tournamentName);

    @Query("SELECT f FROM FightInfo f WHERE (f.fighterOneDocument = :fighterOneDocument AND f.fighterTwoDocument = :fighterTwoDocument) OR (f.fighterOneDocument = :fighterTwoDocument AND f.fighterTwoDocument = :fighterOneDocument)")
    Optional<List<FightInfo>> findAllByFighterOneDocumentAndFighterTwoDocument(String fighterOneDocument, String fighterTwoDocument);

    Optional<FightInfo> findByFightId(Long id);

}
