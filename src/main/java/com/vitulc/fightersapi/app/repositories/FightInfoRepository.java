package com.vitulc.fightersapi.app.repositories;


import com.vitulc.fightersapi.app.dtos.FightsHistoryDto;
import com.vitulc.fightersapi.app.entities.FightInfo;
import com.vitulc.fightersapi.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FightInfoRepository extends JpaRepository<FightInfo, Long> {

    Optional<List<FightInfo>> findFightHistoryByUserOrderByIdDesc(Users user);

    Optional<FightInfo> findByFightId(Long id);
}
