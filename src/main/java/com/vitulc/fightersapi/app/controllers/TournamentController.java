package com.vitulc.fightersapi.app.controllers;

import com.vitulc.fightersapi.app.dtos.*;
import com.vitulc.fightersapi.app.entities.Tournament;
import com.vitulc.fightersapi.app.services.TournamentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournament")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create (@RequestBody @Valid TournamentDto tournamentDto){
        return tournamentService.create(tournamentDto);
    }

    @PostMapping("/addFighters")
    public ResponseEntity<String> addFightersInTournament(@RequestBody @Valid TournamentFighterDto tournamentFighterDto) {
        return tournamentService.addFightersInTournament(tournamentFighterDto);
    }

    @PostMapping("/winner")
    public ResponseEntity<String> setWinnerTournament(@RequestBody @Valid TournamentWinnerDto tournamentWinnerDto) {
        return tournamentService.setWinnerTournament(tournamentWinnerDto);
    }

    @GetMapping()
    public ResponseEntity<List<TournamentResponseDto>> getTournaments() {
        return tournamentService.getTournaments();
    }

    @GetMapping("/details")
    public ResponseEntity<TournamentDetailsDto> getTournamentDetails(@RequestParam("tournamentName") String tournamentName) {
        return tournamentService.getTournamentDetails(tournamentName);
    }
}
