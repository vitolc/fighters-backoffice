package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.*;
import com.vitulc.fightersapi.app.entities.FightInfo;
import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.entities.Tournament;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.FightInfoRepository;
import com.vitulc.fightersapi.app.repositories.FighterRepository;
import com.vitulc.fightersapi.app.repositories.TournamentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final FighterRepository fighterRepository;
    private final AuthenticationService authenticationService;
    private final FightInfoRepository fightInfoRepository;

    public TournamentService(
            TournamentRepository tournamentRepository,
            FighterRepository fighterRepository,
            AuthenticationService authenticationService,
            FightInfoRepository fightInfoRepository) {

        this.tournamentRepository = tournamentRepository;
        this.fighterRepository = fighterRepository;
        this.authenticationService = authenticationService;
        this.fightInfoRepository = fightInfoRepository;
    }

    public ResponseEntity<String> create(TournamentDto tournamentDto) {

        if (tournamentRepository.findByUserAndName(authenticationService.getCurrentUser(), tournamentDto.tournamentName()).isPresent()) {
            throw new BadRequestException("Tournament with the same name already exists");
        }

        var tournament = new Tournament(tournamentDto);
        tournament.setUser(authenticationService.getCurrentUser());
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Tournament created successfully");
    }

    public ResponseEntity<String> addFightersInTournament(TournamentFighterDto tournamentFighterDto) {
        var tournament = tournamentRepository.findByUserAndId(authenticationService.getCurrentUser(), tournamentFighterDto.tournamentId())
                .orElseThrow(() -> new NotFoundException("Tournament not found"));

        Fighter fighter = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(authenticationService.getCurrentUser(), tournamentFighterDto.document())
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        if (tournament.getFighters().contains(fighter)) {
            return ResponseEntity.badRequest().body("Fighter is already in the tournament");
        }

        tournament.getFighters().add(fighter);
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Fighter added to the tournament successfully");
    }

    public ResponseEntity<String> setWinnerTournament(TournamentWinnerDto tournamentWinnerDto) {
        var tournament = tournamentRepository.findByUserAndId(authenticationService.getCurrentUser(), tournamentWinnerDto.tournamentId())
                .orElseThrow(() -> new NotFoundException("Tournament not found"));

        Fighter winner = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(
                        authenticationService.getCurrentUser(), tournamentWinnerDto.document())
                .orElseThrow(() -> new NotFoundException("Winner fighter not found"));

        List<Fighter> tournamentFighters = tournament.getFighters();

        if (!tournamentFighters.contains(winner)) {
            throw new BadRequestException("The fighter should be part of the tournament");
        }

        tournament.setWinner(winner);
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Winner set successfully");
    }

    public ResponseEntity<TournamentDetailsDto> getTournamentDetails(String tournamentName) {
        var tournament = getTournament(tournamentName);
        return ResponseEntity.ok(createTournamentDetailsDto(tournament));
    }

    public ResponseEntity<List<TournamentResponseDto>> getTournaments() {

        List<Tournament> tournaments = tournamentRepository.findByUser(authenticationService.getCurrentUser());

        List<TournamentResponseDto> tournamentList = tournaments.stream()
                .map(TournamentResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tournamentList);
    }

    public ResponseEntity<List<FighterResponseDto>> getAllFightersInTournament(String tournamentName){
        return ResponseEntity.ok(getAllFightersByTournament(tournamentName));
    }

    public ResponseEntity<List<FightsHistoryDto>> getAllFightsInTournament(String tournamentName){
        return ResponseEntity.ok(getAllFightsByTournament(tournamentName));
    }

    public Tournament validateTournament(FightDto fightDto, Fighter fighterOne, Fighter fighterTwo) {
        if (fightDto.tournamentId() == null) return null;

        Tournament tournament = tournamentRepository.findByUserAndId(authenticationService.getCurrentUser(), fightDto.tournamentId())
                .orElseThrow(() -> new NotFoundException("Tournament not found"));

        List<Fighter> tournamentFighters = tournament.getFighters();

        if (!tournamentFighters.contains(fighterOne) || !tournamentFighters.contains(fighterTwo)) {
            throw new BadRequestException("Both fighters should be part of the tournament");
        }

        return tournament;
    }

    public Tournament getTournament(String tournamentName) {
        return tournamentRepository.findByUserAndName(authenticationService.getCurrentUser(), tournamentName)
                .orElseThrow(() -> new NotFoundException("Tournament not found"));
    }

    public List<FighterResponseDto> getAllFightersByTournament(String tournamentName) {

        var tournament = getTournament(tournamentName);

        return tournament.getFighters().stream()
                .map(FighterResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<FightsHistoryDto> getAllFightsByTournament(String tournamentName) {

        List<FightInfo> fightInfos = fightInfoRepository.findFightHistoryByUserAndTournamentNameOrderByIdDesc(authenticationService.getCurrentUser(), tournamentName)
                .orElseThrow(() -> new NotFoundException("There are no fights to list"));

        return fightInfos.stream()
                .map(FightsHistoryDto::new)
                .collect(Collectors.toList());
    }

    public TournamentDetailsDto createTournamentDetailsDto(Tournament tournament) {

        String tournamentWinnerName = null;
        String tournamentWinnerDocument = null;

        if (tournament.getWinner() != null){
            tournamentWinnerName = tournament.getWinner().getName();
            tournamentWinnerDocument = tournament.getWinner().getDocument();
        }

        return new TournamentDetailsDto(
                tournament.getName(),
                tournament.getId(),
                getAllFightersByTournament(tournament.getName()),
                getAllFightsByTournament(tournament.getName()),
                tournamentWinnerName,
                tournamentWinnerDocument);
    }

}
