package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.FightDto;
import com.vitulc.fightersapi.app.dtos.FightsHistoryDto;
import com.vitulc.fightersapi.app.dtos.FightWinnerDto;
import com.vitulc.fightersapi.app.entities.*;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.ConflictException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FightService {

    private final FighterRepository fighterRepository;
    private final AuthenticationService authenticationService;
    private final FightRepository fightRepository;
    private final CategoryService categoryService;
    private final TournamentRepository tournamentRepository;
    private final FightInfoRepository fightInfoRepository;

    public FightService(
            FighterRepository fighterRepository,
            AuthenticationService authenticationService,
            FightRepository fightRepository,
            CategoryService categoryService,
            TournamentRepository tournamentRepository,
            FightInfoRepository fightInfoRepository) {

        this.fighterRepository = fighterRepository;
        this.authenticationService = authenticationService;
        this.fightRepository = fightRepository;
        this.categoryService = categoryService;
        this.tournamentRepository = tournamentRepository;
        this.fightInfoRepository = fightInfoRepository;
    }

    public ResponseEntity<String> create(FightDto fightDto) {

        var fighterOne = fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), fightDto.FighterOneDocument())
                .orElseThrow(() -> new NotFoundException("Fighter one not found"));

        var fighterTwo = fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), fightDto.FighterTwoDocument())
                .orElseThrow(() -> new NotFoundException("Fighter two not found"));

        if (fightDto.FighterOneDocument().equals(fightDto.FighterTwoDocument())) {
            throw new BadRequestException("Fighters cannot be the same");
        }

        Category category = null;

        if (fightDto.categoryId() != null) {
            category = categoryService.getByIdAndCategoryGroupUser(fightDto.categoryId(), authenticationService.getCurrentUser());

            if (fighterOne.getWeight() < category.getMinWeight() || fighterOne.getWeight() > category.getMaxWeight()) {
                throw new BadRequestException("Fighter one weight is not within the category limits");
            }

            if (fighterTwo.getWeight() < category.getMinWeight() || fighterTwo.getWeight() > category.getMaxWeight()) {
                throw new BadRequestException("Fighter two weight is not within the category limits");
            }
        }

        Tournament tournament = null;

        if (fightDto.tournamentId() != null) {
            tournament = tournamentRepository.findByUserAndId(authenticationService.getCurrentUser(), fightDto.tournamentId())
                    .orElseThrow(() -> new NotFoundException("Tournament not found"));

            List<Fighter> tournamentFighters = tournament.getFighters();

            if (!tournamentFighters.contains(fighterOne) || !tournamentFighters.contains(fighterTwo)) {
                throw new BadRequestException("Both fighters should be part of the tournament");
            }
        }

        LocalDateTime dateTime = null;

        if (fightDto.date() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy H:m");
                dateTime = LocalDateTime.parse(fightDto.date(), formatter);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("The date provided is not valid. a correct format, for example, would be '23/3/2023 14:30'");
            }
        }

        var fight = new Fight(fighterOne, fighterTwo, category, dateTime, tournament);

        fight.setUser(authenticationService.getCurrentUser());
        fightRepository.save(fight);
        saveFightInHistory(fight);
        return ResponseEntity.ok("Fight created successfully");
    }

    public ResponseEntity<String> setWinnerFighter(FightWinnerDto fightWinnerDto)  {

        Fight fight = fightRepository.findByIdAndUser(fightWinnerDto.fightId(), authenticationService.getCurrentUser())
                .orElseThrow(() -> new NotFoundException("Fight not found"));

        var winnerFighter = fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), fightWinnerDto.document())
                .orElseThrow(() -> new NotFoundException("There is no fighter with this document"));

        if (!fight.getFighterOne().getDocument().equals(winnerFighter.getDocument()) && !fight.getFighterTwo().getDocument().equals(winnerFighter.getDocument())) {
            throw new BadRequestException("This fighter is not present in this fight");
        }

        if (fightRepository.existsByWinnerAndUserAndId(winnerFighter, authenticationService.getCurrentUser(), fightWinnerDto.fightId())) {
            throw new ConflictException("Winner fighter already set for this fight");
        }

        var fightInfo = fightInfoRepository.findByFightId(fight.getId()).orElseThrow();

        fight.setWinner(winnerFighter);
        fightRepository.save(fight);
        fightInfo.setWinnerName(winnerFighter.getName());
        fightInfoRepository.save(fightInfo);
        return ResponseEntity.ok("Winner fighter set successfully");
    }

    public ResponseEntity<List<FightsHistoryDto>> getAllFightsByUser() {

        List<FightInfo> fightInfos = fightInfoRepository.findFightHistoryByUserOrderByIdDesc(authenticationService.getCurrentUser())
                .orElseThrow(() -> new NotFoundException("There are no fights to list"));

        List<FightsHistoryDto> fightsHistoryDto = fightInfos.stream()
                .map(FightsHistoryDto::new)
                .collect(Collectors.toList());

        if (fightsHistoryDto.isEmpty()) {
            throw new NotFoundException("There are no fights to list");
        }

        return ResponseEntity.ok(fightsHistoryDto);
    }

    public void saveFightInHistory(Fight fight){
        FightInfo fightInfo = new FightInfo(fight.getFighterOne(), fight.getFighterTwo());
        fightInfo.setUser(authenticationService.getCurrentUser());
        fightInfo.setFightId(fight.getId());
        fightInfoRepository.save(fightInfo);
    }

}
