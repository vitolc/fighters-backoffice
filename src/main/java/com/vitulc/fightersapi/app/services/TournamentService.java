package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.TournamentDto;
import com.vitulc.fightersapi.app.dtos.TournamentFighterDto;
import com.vitulc.fightersapi.app.dtos.FightWinnerDto;
import com.vitulc.fightersapi.app.dtos.TournamentWinnerDto;
import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.entities.Tournament;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.FighterRepository;
import com.vitulc.fightersapi.app.repositories.TournamentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final FighterRepository  fighterRepository;
    private final AuthenticationService authenticationService;

    public TournamentService(
            TournamentRepository tournamentRepository,
            FighterRepository fighterRepository,
            AuthenticationService authenticationService) {
        this.tournamentRepository = tournamentRepository;
        this.fighterRepository = fighterRepository;
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<String> create(TournamentDto tournamentDto){
        var tournament = new Tournament(tournamentDto);
        tournament.setUser(authenticationService.getCurrentUser());
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Tournament created successfully");
    }

    public ResponseEntity<String> addFightersInTournament(TournamentFighterDto tournamentFighterDto){
        var tournament = tournamentRepository.findByUserAndId(authenticationService.getCurrentUser(), tournamentFighterDto.tournamentId())
                .orElseThrow(() -> new NotFoundException("Tournament not found"));

        Fighter fighter = fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), tournamentFighterDto.document())
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        if (tournament.getFighters().contains(fighter)) {
            return ResponseEntity.badRequest().body("Fighter is already in the tournament");
        }

        tournament.getFighters().add(fighter);
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Fighter added to the tournament successfully");
    }

    public ResponseEntity<String> setWinnerTournament(TournamentWinnerDto tournamentWinnerDto){
        var tournament = tournamentRepository.findByUserAndId(authenticationService.getCurrentUser(), tournamentWinnerDto.tournamentId())
                .orElseThrow(() -> new NotFoundException("Tournament not found"));

        Fighter winner = fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), tournamentWinnerDto.document())
                .orElseThrow(() -> new NotFoundException("Winner fighter not found"));

        List<Fighter> tournamentFighters = tournament.getFighters();

        if (!tournamentFighters.contains(winner)) {
            throw new BadRequestException("The fighter should be part of the tournament");
        }

        tournament.setWinner(winner);
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Winner set successfully");
    }
}
