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
import java.util.List;
import java.util.stream.Collectors;

import static com.vitulc.fightersapi.app.services.DateTimeService.parseDate;

@Service
public class FightService {

    private final FighterRepository fighterRepository;
    private final AuthenticationService authenticationService;
    private final FightRepository fightRepository;
    private final CategoryService categoryService;
    private final FightInfoRepository fightInfoRepository;
    private final TournamentService tournamentService;

    public FightService(
            FighterRepository fighterRepository,
            AuthenticationService authenticationService,
            FightRepository fightRepository,
            CategoryService categoryService,
            FightInfoRepository fightInfoRepository,
            TournamentService tournamentService) {

        this.fighterRepository = fighterRepository;
        this.authenticationService = authenticationService;
        this.fightRepository = fightRepository;
        this.categoryService = categoryService;
        this.fightInfoRepository = fightInfoRepository;
        this.tournamentService = tournamentService;
    }

    public ResponseEntity<String> create(FightDto fightDto) {

        var fighterOne = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(authenticationService.getCurrentUser(), fightDto.FighterOneDocument())
                .orElseThrow(() -> new NotFoundException("Fighter one not found"));

        var fighterTwo = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(authenticationService.getCurrentUser(), fightDto.FighterTwoDocument())
                .orElseThrow(() -> new NotFoundException("Fighter two not found"));

        if (fightDto.FighterOneDocument().equals(fightDto.FighterTwoDocument())) {
            throw new BadRequestException("Fighters cannot be the same");
        }

        var category = categoryService.validateCategory(fightDto, fighterOne, fighterTwo);
        var tournament = tournamentService.validateTournament(fightDto, fighterOne, fighterTwo);
        var dateTime = parseDate(fightDto.date());

        var fight = new Fight(fighterOne, fighterTwo, category, dateTime, tournament);
        fight.setUser(authenticationService.getCurrentUser());
        fightRepository.save(fight);
        saveFightInHistory(fight);
        return ResponseEntity.ok("Fight created successfully");
    }

    public ResponseEntity<String> setWinnerFighter(FightWinnerDto fightWinnerDto) {

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
        fightInfo.setWinnerDocument(winnerFighter.getDocument());
        fightInfoRepository.save(fightInfo);
        return ResponseEntity.ok("Winner fighter set successfully");
    }

    public ResponseEntity<List<FightsHistoryDto>> getAllFightsByUser() {

        List<FightInfo> fightInfos = fightInfoRepository.findFightHistoryByUserOrderByIdDesc(authenticationService.getCurrentUser())
                .orElseThrow(() -> new NotFoundException("There are no fights to list"));

        if(fightInfos.isEmpty()){
            throw new NotFoundException("There are no fights to list");
        }

        List<FightsHistoryDto> fightsHistoryDto = fightInfos.stream()
                .map(FightsHistoryDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(fightsHistoryDto);
    }

    public ResponseEntity<List<FightsHistoryDto>> getAllFightsBetweenTwoFighters(String fighterOneDocument, String fighterTwoDocument) {

        fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), fighterOneDocument)
                .orElseThrow(() -> new NotFoundException("Fighter one not found"));

        fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), fighterTwoDocument)
                .orElseThrow(() -> new NotFoundException("Fighter two not found"));

        List<FightInfo> fightInfos = fightInfoRepository.findAllByFighterOneDocumentAndFighterTwoDocument(fighterOneDocument, fighterTwoDocument)
                .orElseThrow(() -> new NotFoundException("these fighters have never faced each other"));

        if(fightInfos.isEmpty()){
            throw new NotFoundException("There are no fights to list");
        }

        List<FightsHistoryDto> fightsHistoryDto = fightInfos.stream()
                .map(FightsHistoryDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(fightsHistoryDto);
    }

    public void saveFightInHistory(Fight fight) {
        FightInfo fightInfo = new FightInfo(fight.getFighterOne(), fight.getFighterTwo());
        fightInfo.setUser(authenticationService.getCurrentUser());
        fightInfo.setFightId(fight.getId());

        String categoryName = null;

        if (fight.getCategory() != null){
            categoryName = fight.getCategory().getCategoryName();
        }

        fightInfo.setCategoryName(categoryName);

        String tournamentName = null;

        if (fight.getTournament() != null){
            tournamentName = fight.getTournament().getName();
        }

        fightInfo.setTournamentName(tournamentName);

        LocalDateTime date = null;

        if (fight.getDate() != null){
            date = fight.getDate();
        }

        fightInfo.setDate(date);

        fightInfoRepository.save(fightInfo);
    }


}
